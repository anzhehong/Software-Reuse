package com.Warehouse.DAO;

import com.Warehouse.entity.User;
import org.springframework.orm.hibernate4.HibernateTemplate;
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

    //已验证
    public User findByName(String username){
        HibernateTemplate hibernateTemplate = new HibernateTemplate(getSessionFactory());
        return (User)hibernateTemplate.find("from User u Where u.userName = ?",username).get(0);
    }
}
