package com.Warehouse.DAO;

import com.Warehouse.entity.User;
import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:49
 */
public interface MainDAO extends GeneralDAO<User> {
    User findByName(String username);
}
