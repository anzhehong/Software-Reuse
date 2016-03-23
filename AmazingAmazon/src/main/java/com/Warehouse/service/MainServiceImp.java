package com.Warehouse.service;

import com.Warehouse.DAO.MainDAO;
import com.Warehouse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:46
 */

@Service
@Transactional
public class MainServiceImp implements MainService {

    @Autowired
    private MainDAO mainDAO;

    @Override
    public List<User> listAllUsers() {
        List<User> list = mainDAO.queryAll();
        return list;
    }
}
