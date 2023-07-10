package com.freel00p.service;

import com.freel00p.domain.ResponseResult;
import com.freel00p.domain.entity.User;

/**
 * BlogLoginService
 *
 * @author fj
 * @since 2023/7/3 13:37
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
