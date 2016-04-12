import org.junit.Test;

import java.util.Date;

/**
 * Created by fowafolo
 * Date: 16/4/6
 * Time: 18:57
 */
public class DateUtilTest {
    @Test
    public void testGetTodayStr() throws Exception {
        System.out.println(DateUtil.getTodayStr());
    }

    @Test
    public void testGetStrByDefaultFormat() throws Exception {
        System.out.println(DateUtil.getStrByDefaultFormat(new Date()));
    }

    @Test
    public void testGetStrByCustomFormat() throws Exception {
        System.out.println(DateUtil.getStrByCustomFormat(new Date(), "hh:mm:ss/ MM dd yy"));
    }

}