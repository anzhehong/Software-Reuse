import org.junit.Before;
import org.junit.Test;
import reuse.license.MultiFrequencyRestriction;

import static org.junit.Assert.*;

public class MultiFrequencyRestrictionTest {

    MultiFrequencyRestriction multiFrequencyRestriction = new MultiFrequencyRestriction(10);

    @Before
    public void setUp() throws Exception {
        multiFrequencyRestriction = new MultiFrequencyRestriction(10);
    }

    @Test
    public void testCheckByKey() throws Exception {
        multiFrequencyRestriction.addMap("first");
        for(int i = 0 ;i < 10;i++){
            System.out.print(i + " ");
            Thread.sleep(50);
            Boolean test = multiFrequencyRestriction.CheckByKey("first");
            System.out.println(test);
            assertEquals(true,test);
        }

        System.out.print(11 + " ");
        Thread.sleep(50);
        Boolean test = multiFrequencyRestriction.CheckByKey("first");
        System.out.println(test);
        assertEquals(false,test);

        Boolean testError = multiFrequencyRestriction.CheckByKey("second");
        System.out.println(testError);
        assertEquals(false,testError);

        Thread.sleep(1001);
        for(int i = 0 ;i < 20;i++){
            System.out.print(i + 12 + " ");
            Thread.sleep(200);
            Boolean testSuccess = multiFrequencyRestriction.CheckByKey("first");
            System.out.println(testSuccess);
            assertEquals(true,testSuccess);
        }




    }
}