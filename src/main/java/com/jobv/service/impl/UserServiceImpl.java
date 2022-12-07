package com.jobv.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jobv.entity.User;
import com.jobv.exception.ServiceException;
import com.jobv.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobv.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 程序员青戈
 * @since 2022-12-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(User user) {
        User dbUser = null;
        try {
            dbUser = getOne(new UpdateWrapper<User>().eq("username", user.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException("系统异常，请稍后再试");
        }

        if (dbUser == null) {
            throw new ServiceException("未找到此用户");
        }
        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        return dbUser;
    }

    @Override
    public User register(User user) {

        try {
            User dbUser = getOne(new UpdateWrapper<User>().eq("username", user.getUsername()));
            if (dbUser != null) {
                throw new ServiceException("用户名已存在");
            }
        } catch (Exception e) {
            throw new RuntimeException("系统异常，请稍后再试");
        }
        return null;
    }
}
