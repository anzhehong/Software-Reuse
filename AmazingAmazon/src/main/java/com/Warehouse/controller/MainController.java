package com.Warehouse.controller;

import com.Warehouse.entity.User;
import com.Warehouse.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 14:54
 */

@Controller
public class MainController {

    @Autowired
    MainService mainService;

    private static List<User> users;

    public static void main(String[] args) throws Exception{

        ApplicationContext beanFactory;
        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");

        MainController mainController = (MainController) beanFactory.getBean("main");

        mainController.test();
    }

    public void test() {
        users = mainService.listAllUsers();


    }
}
