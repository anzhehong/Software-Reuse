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

    @Override
    public boolean insertUser(User user) {
        List<User> list = mainDAO.queryAll();

        for(int i = 0;i < list.size();i++){
            if((user.getUserName().equals(list.get(i).getUserName())))
            {
                return false;
            }
        }
        mainDAO.insert(user);
        return  true;
    }

    @Override
    public User findByName(String userName){
        List<User> userList = mainDAO.queryAll();
        if (userList.size() == 0) {
            return null;
        }else {
            for (int i = 0 ; i < userList.size(); i ++) {
                User user = userList.get(i);
                if (user.getUserName().equals(userName))
                    return user;
            }
            return null;
        }
    }
}
