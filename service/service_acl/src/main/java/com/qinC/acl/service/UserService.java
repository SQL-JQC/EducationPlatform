package com.qinC.acl.service;

import com.qinC.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-12-01
 */
public interface UserService extends IService<User> {

    User selectByUsername(String username);

}
