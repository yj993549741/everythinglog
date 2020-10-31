package com.keymonster.everythinglog.service;

import com.keymonster.everythinglog.dao.UserRepository;
import com.keymonster.everythinglog.poject.User;
import com.keymonster.everythinglog.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by yangjie sheting on 2020/10/25
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String name, String password) {
        User user=userRepository.findByUsernameAndPassword(name, Md5Util.code(password));
        return user;
    }
}
