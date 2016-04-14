import org.junit.Before;
import org.junit.Test;
import reuse.license.FrequencyRestriction;

import static org.junit.Assert.*;

public class FrequencyRestrictionTest {

    FrequencyRestriction frequencyRestriction;

    @Before
    public void setUp() throws Exception {
        frequencyRestriction = new FrequencyRestriction(10);
    }

    @Test
    public void testCheck() throws Exception {
        for(int i = 0 ;i < 10;i++){
            System.out.print(i + " ");
            Thread.sleep(50);
            Boolean test = frequencyRestriction.Check();
            System.out.println(test);
            assertEquals(true,test);
        }
        for(int i = 0 ;i < 2;i++){
            System.out.print(i+10 + " ");
            Thread.sleep(50);
            Boolean test = frequencyRestriction.Check();
            System.out.println(test);
            assertEquals(false,test);
        }
        Thread.sleep(1001);
        for(int i = 0 ;i<10;i++){
            System.out.print(i+12 + " ");
            Thread.sleep(50);
            Boolean test = frequencyRestriction.Check();
            System.out.println(test);
            assertEquals(true, test);
        }
        for(int i = 0 ;i < 2;i++){
            System.out.print(i+22 + " ");
            Thread.sleep(50);
            Boolean test = frequencyRestriction.Check();
            System.out.println(test);
            assertEquals(false,test);
        }

    }
}