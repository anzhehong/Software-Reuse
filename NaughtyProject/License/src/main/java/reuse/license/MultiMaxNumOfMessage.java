package reuse.license;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MSI on 2016/4/6.
 */
public class MultiMaxNumOfMessage implements MessageRestriction{

    private Map<String, Integer> arrayListMap = new HashMap<String, Integer>();
    private int MaxNumOfMessage;

    public MultiMaxNumOfMessage(int maxNumOfMessage) {
        MaxNumOfMessage = maxNumOfMessage;
    }

    public void addMap(String key){
        arrayListMap.put(key,new Integer(0));
    }

    public void autoAddByAutoKey(){
        arrayListMap.put("autoName" + arrayListMap.size(),new Integer(0));
    }

    public void deleteMap(String key){
        arrayListMap.remove(key);
    }

    public int getMaxNumOfMessage() {
        return MaxNumOfMessage;
    }

    public boolean CheckByKey(String key) {
        if(arrayListMap.containsKey(key)){
            arrayListMap.put(key,arrayListMap.get(key)+1);
            if(arrayListMap.get(key) > MaxNumOfMessage)
            {
                arrayListMap.put(key,0);
                return false;
            }
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean Check() {
        return false;
    }

//    public static void main(String[] args) {
//        reuse.license.MultiMaxNumOfMessage multiMaxNumOfMessage = new reuse.license.MultiMaxNumOfMessage(10);
//        multiMaxNumOfMessage.addMap("first");
//        multiMaxNumOfMessage.addMap("second");
//        boolean first;
//        boolean second;
//        boolean third;
//        for(int i= 0 ; i< multiMaxNumOfMessage.getMaxNumOfMessage() + 5;i++) {
//            first = multiMaxNumOfMessage.CheckByKey("first");
//            second = multiMaxNumOfMessage.CheckByKey("second");
//            third = multiMaxNumOfMessage.CheckByKey("third");
//            System.out.println(i + "first:\t"+ first);
//            System.out.println(i + "second:\t"+ second);
//            System.out.println(i + "third:\t"+ third);
//
//        }
//    }
}
