package com.Internal.Service;


import com.Internal.Entity.User;

import java.util.List;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:46
 */
public interface MainService {

    /**
     * 列出所有User
     * @return User集合
     */
    public List<User> listAllUsers();

    /**
     * 插入一个User
     * @param user User类型实例
     */
    public boolean insertUser(User user);

    /**
     * 通过用户名查找User
     * @param userName 用户名
     * @return 指定User实例
     */
    public User findByName(String userName);
}
