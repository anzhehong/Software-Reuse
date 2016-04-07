import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MSI on 2016/4/6.
 */
public class FrequencyRestriction implements MessageRestriction{

    private int NumOfMessagePerSecond;
    private List<Date> dates = new ArrayList<Date>();

    public FrequencyRestriction(int numOfMessagePerSecond) {
        NumOfMessagePerSecond = numOfMessagePerSecond;

    }

    @Override
    public boolean Check() {
        Date now = new Date();
        dates.add(now);
        if(dates.size() <= NumOfMessagePerSecond)
            return true;
        else{
            Date lastDate = dates.get(dates.size() - NumOfMessagePerSecond - 1);
            double interval = getTimeInterval(now,lastDate);
            System.out.println("internal:" + interval);
            if(interval > 1)
                return true;
            else {
                dates.remove(dates.size() - 1);
                return false;
            }
        }
    }

    static public double getTimeInterval(Date date1, Date date2) {
        long time1=date1.getTime();
        long time2=date2.getTime();
        long test=Math.abs(time2-time1);
        return (double)test / 1000;
    }

    public static void main(String[] args) throws InterruptedException {
        MessageRestriction ff = new FrequencyRestriction(10);
        for(int i = 0 ;i<12;i++){
            System.out.print(i + " ");
            Thread.sleep(50);
            System.out.println(ff.Check());
        }
        Thread.sleep(1001);
        for(int i = 0 ;i<12;i++){
            System.out.print(i+12 + " ");
            Thread.sleep(50);
            System.out.println(ff.Check());
        }
    }
}
