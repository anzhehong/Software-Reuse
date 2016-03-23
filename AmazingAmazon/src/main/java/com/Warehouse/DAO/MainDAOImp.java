package com.Warehouse.DAO;

import com.Warehouse.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:50
 */

@Repository
public class MainDAOImp extends GeneralDAOImp<User> implements MainDAO {

    public MainDAOImp()
    {
        super(User.class);
    }
}
