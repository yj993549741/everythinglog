package com.keymonster.everythinglog.dao;

import com.keymonster.everythinglog.poject.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by yangjie sheting on 2020/10/25
 */
public interface UserRepository extends JpaRepository<User ,Long> {
     User findByUsernameAndPassword(String username,String password);
}
