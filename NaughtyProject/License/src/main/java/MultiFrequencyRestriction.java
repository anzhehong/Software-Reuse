import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by MSI on 2016/4/6.
 */
public class MultiFrequencyRestriction implements MessageRestriction{

    private static Map<String, ArrayList<Date>> arrayListMap = new HashMap<String, ArrayList<Date>>();
    private int NumOfMessagePerSecond;

    public MultiFrequencyRestriction(int numOfMessagePerSecond) {
        NumOfMessagePerSecond = numOfMessagePerSecond;
    }

    public void addMap(String key){
        arrayListMap.put(key,new ArrayList<Date>());
    }

    public void autoAddByAutoKey(){
        arrayListMap.put("autoName" + arrayListMap.size(),new ArrayList<Date>());
    }

    public void deleteMap(String key){
        arrayListMap.remove(key);
    }

    static public double getTimeInterval(Date date1, Date date2) {
        long time1=date1.getTime();
        long time2=date2.getTime();
        long test=Math.abs(time2-time1);
        return (double)test / 1000;
    }

    public boolean CheckByKey(String key){
        if(arrayListMap.containsKey(key)) {
            ArrayList<Date> dates = arrayListMap.get(key);
            Date now = new Date();
            dates.add(now);
            if (dates.size() <= NumOfMessagePerSecond)
                return true;
            else {
                Date lastDate = dates.get(dates.size() - NumOfMessagePerSecond -1);
                double interval = getTimeInterval(now, lastDate);
                if (interval > 1) {
                    return true;
                }
                else {
                    dates.remove(dates.size() - 1);
                    return false;
                }
            }
        }
        else
            return false;
    }

    @Override
    public boolean Check() {
        return false;
    }

//    public static void main(String[] args) throws InterruptedException {
//        MultiFrequencyRestriction multiFrequencyRestriction = new MultiFrequencyRestriction(10);
//        multiFrequencyRestriction.addMap("first");
//        for(int i = 0 ;i < 10;i++){
//            System.out.print(i + " ");
//            Thread.sleep(50);
//            Boolean test = multiFrequencyRestriction.CheckByKey("first");
//            System.out.println(test);
//
//        }
//
//        System.out.print(11 + " ");
//        Thread.sleep(50);
//        Boolean test = multiFrequencyRestriction.CheckByKey("first");
//        System.out.println(test);
//
//
//        Boolean testError = multiFrequencyRestriction.CheckByKey("second");
//        System.out.println(testError);
//
//        Thread.sleep(1001);
//        for(int i = 0 ;i < 20;i++){
//            System.out.print(i + " ");
//            Thread.sleep(200);
//            Boolean testSuccess = multiFrequencyRestriction.CheckByKey("first");
//            System.out.println(test);
//
//        }
//
//
//    }
}