import com.Warehouse.controller.MainController;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

/** 
* MainController Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 28, 2016</pre> 
* @version 1.0 
*/ 
public class MainControllerTest {

    private MainController mainController;

    @Before
    public void before() throws Exception {
        ApplicationContext beanFactory;
        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
            mainController = (MainController) beanFactory.getBean("main");
    }

    @After
    public void after() throws Exception {

    }

    /**
    *
    * Method: main(String[] args)
    *
    */
    @Test
    public void testMain() throws Exception {
        //TODO: Test goes here...

        boolean flag = mainController.checkPassword("abc", "abc");
        System.out.println(flag);


    }

    /**
    *
    * Method: test()
    *
    */
    @Test
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

    }


    /**
     *
     * Method: registerUser(String username, String pwd)
     *
     */
    @Test
    @Rollback(true)
    public void testRegisterUser() throws Exception {
    //TODO: Test goes here...
        boolean flag = mainController.registerUser("ac","ABC");
        System.out.println(flag);

    }
}
