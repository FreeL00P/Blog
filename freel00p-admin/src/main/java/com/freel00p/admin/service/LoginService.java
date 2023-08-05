package com.freel00p.admin.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.User;

/**
 * LoginService
 *
 * @author fj
 * @since 2023/8/5 22:01
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
