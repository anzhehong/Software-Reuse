package reuse.utility;

import java.util.Date;

/**
 * Created by fowafolo
 * Date: 16/3/28
 * Time: 23:52
 */

/**
 * 返回的是时间差秒数
 */
public class TimeUtil {
    /**
     * 计算两个Date类型数据相差的秒数
     * @param date1
     * @param date2
     * @return
     */
    static public long getTimeInterval(Date date1, Date date2) {
        long time1=date1.getTime();
        long time2=date2.getTime();
        long test=Math.abs(time2-time1);
        return test / 1000;
    }
}
