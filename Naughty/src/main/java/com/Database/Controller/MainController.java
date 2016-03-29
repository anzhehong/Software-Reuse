package com.Database.Controller;

import com.Communication.Entity.User;
import com.Config.ConfigData;
import com.Config.GlobalConfig;
import com.Database.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
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

//        ApplicationContext beanFactory;
//        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
//
//        MainController mainController = (MainController) beanFactory.getBean("main");

//        mainController.testConfig();
        System.out.println(ConfigData.getBaseQueueDestination());
    }

    public void test() throws JMSException {
        User user = mainService.findByName("abc");
        System.out.println(user.getUserPassword());
        System.out.println(checkPassword("abc", "abc"));
    }

    @Autowired
    GlobalConfig globalConfig;
    public void testConfig (){
        System.out.println(globalConfig.getLoginRefused());
    }

    public boolean checkPassword(String username,String password)throws JMSException {
        User user = mainService.findByName(username);
        if (user == null) {
            return false;
        }
        System.out.println(user.getUserName());
        if(password.equals(user.getUserPassword()))
            return true;
        else
            return false;
    }

    public boolean registerUser(String username,String pwd){
        return mainService.insertUser(new User(username,pwd));
    }
}
