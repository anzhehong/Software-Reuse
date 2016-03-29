package controller;

import com.Warehouse.controller.MainController;
import com.Warehouse.entity.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

/** 
* MainController Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 28, 2016</pre> 
* @version 1.0 
*/ 
public class MainControllerTest { 
    private MainController mainController = new MainController();
    private User user = new User("abc","abc");
@Before
public void before() throws Exception {
    ApplicationContext beanFactory;
    beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");

   mainController = (MainController) beanFactory.getBean("main");
    System.out.println("test begin...");

} 

@After
public void after() throws Exception {
    System.out.println("test end...");
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Ignore
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: test() 
* 
*/ 
@Ignore
public void testTest() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: checkPassword(String username, String password) 
* 
*/ 
@Test
public void testCheckPassword() throws Exception { 
//TODO: Test goes here...
    assertEquals(true,mainController.checkPassword(user.getUserName(),user.getUserPassword()));
} 

/** 
* 
* Method: registerUser(String username, String pwd) 
* 
*/ 
@Test
public void testRegisterUser() throws Exception {
    assertEquals(false,mainController.registerUser(user.getUserName(),user.getUserPassword()));
//TODO: Test goes here... 
}

} 
