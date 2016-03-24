package com.Warehouse.DAO;

import com.Warehouse.entity.User;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:49
 */
public interface MainDAO extends GeneralDAO<User> {

    /**
     * 通过用户名取用户数据
     * @param username 用户姓名
     * @return 用户
     */
    User findByName(String username);
}
