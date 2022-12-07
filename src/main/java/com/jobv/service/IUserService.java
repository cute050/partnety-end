package com.jobv.service;

import com.jobv.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 程序员青戈
 * @since 2022-12-04
 */
public interface IUserService extends IService<User> {

    User login(User user);

    User register(User user);
}
