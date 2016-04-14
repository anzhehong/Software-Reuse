import org.junit.Before;
import org.junit.Test;
import reuse.license.MaxNumOfMessage;

import static org.junit.Assert.*;

public class MaxNumOfMessageTest {


    MaxNumOfMessage maxNumOfMessage;
    @Before
    public void setUp() throws Exception {
        maxNumOfMessage = new MaxNumOfMessage(10);
    }

    @Test
    public void testCheck() throws Exception {
        for(int i= 0 ; i< maxNumOfMessage.getMaxNumOfMessage() + 5;i++) {

            Boolean test = maxNumOfMessage.Check();
            System.out.println(i + "\t"+ test);
            if(i == maxNumOfMessage.getMaxNumOfMessage()){
                assertEquals(false,test);
            }
            else{
                assertEquals(true,test);
            }

        }

    }
}