package com.jobv.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jobv.common.Constants;
import com.jobv.entity.User;
import com.jobv.exception.ServiceException;
import com.jobv.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobv.service.IUserService;
import com.jobv.utils.EmailUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 程序员jobv
 * @since 2022-12-04
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    private static final Map<String,Long> CODE_MAP =new ConcurrentHashMap<>();

    private final EmailUtils emailUtils;

    /**
     * 登录
     *
     * @param user
     * @return
     */
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

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public User register(User user) {

        try {
            return saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("系统异常，请稍后再试");
        }

    }


    /**
     * 注册中方法提取
     *
     * @param user
     * @return
     */
    private User saveUser(User user) {
        User dbUser = getOne(new UpdateWrapper<User>().eq("username", user.getUsername()));

        if (StrUtil.isBlank(user.getName())) {
            String name = Constants.USER_NAME_PREFIX +
                    DateUtil.format(new Date(), Constants.DATE_RULE) +
                    RandomUtil.randomNumbers(6);
            user.setName(name);
        }

        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword("aB!@12[]");
        }

        if (dbUser != null) {
            throw new ServiceException("用户名已存在");
        }

        user.setUid(IdUtil.fastSimpleUUID());

        boolean save = save(user);
        if (!save) {
            throw new RuntimeException("注册失败");
        }
        return user;
    }


    /**
     * 发送邮件
     *
     * @param email
     * @param type
     */
    @Override
    public void sendEmail(String email, String type) {
        String code = RandomUtil.randomNumbers(6);
        log.info(code);
        String context = "[Partnety]欢迎使用Partnety交友网,验证码{}，有效期5分钟。" +
                "如非本人操作，请检查账号安全";
        String html = StrUtil.format(context, code);
        if ("REGISTER".equals(type)) {

            User user = getOne(new QueryWrapper<User>().eq("email", email));
            if (user != null) {
                throw new ServiceException("邮箱已注册！");
            }
            ThreadUtil.execAsync(() -> {
                        emailUtils.sendHtml("【Partenty】邮箱注册验证", html,email);
                    });
            CODE_MAP.put(code,System.currentTimeMillis());
        }
    }
}
