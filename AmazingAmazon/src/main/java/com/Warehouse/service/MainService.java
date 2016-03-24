package com.Warehouse.service;

import com.Warehouse.entity.User;

import java.util.List;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:46
 */
public interface MainService {

    public List<User> listAllUsers();
    public void insertUser(User user);
    public User findByName(String userName);
}
