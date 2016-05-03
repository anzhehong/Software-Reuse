package com.API.Controller;

import com.Internal.Entity.User;
import com.Internal.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fowafolo
 * Date: 16/4/6
 * Time: 18:42
 */

@Controller
public class DBAPI {

    @Autowired
    MainService mainService;

    /**
     * 检查用户名密码接口
     * @param username
     * @param password
     * @return
     */
    static public boolean CheckPassword(String username,String password) {
        return DBAPI.getDBAPI().checkPasswordAPI(username, password);
    }

    private boolean checkPasswordAPI(String username,String password) {
        User user = mainService.findByName(username);
        if (user == null) {
            return false;
        }
        if(password.equals(user.getUserPassword()))
            return true;
        else
            return false;
    }

    /**
     * 新的检查用户名密码接口,可以返回用户的所在组的ID
     * @param username
     * @param password
     * @return
     */
    static public Map<String, Object> checkPasswordAndGetGroup(String username, String password) {
        return DBAPI.getDBAPI().checkPasswordAndGetGroupAPI(username, password);
    }

    public Map<String, Object> checkPasswordAndGetGroupAPI(String username, String password) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = mainService.findByName(username);
        if (user == null) {
            result.put("error", true);
            result.put("errorMsg", "User Not Found");
        }else {
            if(password.equals(user.getUserPassword())) {
                result.put("error", false);
                result.put("groupId", user.getGroupId());
            }else {
                result.put("error", true);
                result.put("errorMsg", "Password Not Valid");
            }
        }
        return result;
    }

    /**
     * 注册接口
     * @param username
     * @param pwd
     * @return
     */
    static public boolean RegisterUser(String username,String pwd) {
        return DBAPI.getDBAPI().registerUserAPI(username, pwd);
    }

    private boolean registerUserAPI(String username,String pwd) {
        return false;
    }

    static public DBAPI getDBAPI() {
        ApplicationContext beanFactory;
        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
        DBAPI dbapi = (DBAPI) beanFactory.getBean("api");
        return dbapi;
    }

    public static void main(String[] args) {
        Map<String, Object> result = DBAPI.checkPasswordAndGetGroup("abc", "abc");
//        System.out.println(result.get("error"));
//        System.out.println(result.get("groupId"));

        boolean a = ((Boolean)result.get("error")).booleanValue();
        System.out.println(a);
    }
}
