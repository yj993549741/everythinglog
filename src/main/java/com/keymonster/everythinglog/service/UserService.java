package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.poject.User;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface UserService {
    User  checkUser(String name,String password);
}
