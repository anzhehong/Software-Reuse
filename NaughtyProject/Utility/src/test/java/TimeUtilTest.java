import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by fowafolo
 * Date: 16/3/30
 * Time: 01:15
 */
public class TimeUtilTest {

    private Date date1;
    private Date date2;

    @Before
    public void setUp() throws Exception {
        date1 = new Date();
        Thread.sleep(5000);
        date2 = new Date();
    }

//    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetTimeInterval() throws Exception {
        System.out.println(TimeUtil.getTimeInterval(date1, date2));
        assertEquals(5, TimeUtil.getTimeInterval(date1, date2));
    }
}