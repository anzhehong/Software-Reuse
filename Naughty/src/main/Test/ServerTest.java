import com.Warehouse.Server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/** 
* Server Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 28, 2016</pre> 
* @version 1.0 
*/ 
public class ServerTest {


   @Before
   public void before() throws Exception {
      System.out.println("---Before");
   }

   @After
   public void after() throws Exception {
      System.out.println("---After \n\n");
   }

   /**
   *
   * Method: main(String[] args)
   *
   */
   @Test
   public void testMain() throws Exception {
      //TODO: Test goes here...

   }

   /**
   *
   * Method: start()
   *
   */
   @Test
   public void testStart() throws Exception {
      //TODO: Test goes here...
      System.out.println("Test Start");
      final Server server = new Server();
      try {
         server.baseConnect.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
               server.receiveQueue(message);
            }
         });
      } catch (JMSException e) {
         e.printStackTrace();
      }
   }


   /**
   *
   * Method: receiveQueue(Message message)
   *
   */
   @Test
   public void testReceiveQueue() throws Exception {
       //TODO: Test goes here...
    System.out.println("---Test Receive Queue");
   }

   /**
   *
   * Method: sendQueue(boolean flag)
   *
   */
   @Test
   public void testSendQueue() throws Exception {
      //TODO: Test goes here...
      System.out.println("---Test Send Queue");
      Server server = new Server();

   }

   /**
   *
   * Method: sendTopic(Message message)
   *
   */
   @Test
   public void testSendTopic() throws Exception {
      //TODO: Test goes here...
      System.out.println("---Test Send Topic");
   }

} 
