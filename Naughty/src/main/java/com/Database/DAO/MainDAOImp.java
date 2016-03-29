package com.Database.DAO;

import com.Application.Entity.User;
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
