import java.util.HashMap;
import java.util.Map;

/**
 * Created by MSI on 2016/4/6.
 */
public class MultiMaxNumOfMessage implements MessageRestriction{

    private static Map<String, Integer> arrayListMap = new HashMap<String, Integer>();
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

    public boolean CheckByKey(String key) {
        if(arrayListMap.containsKey(key)){
            Integer CurrentNum = arrayListMap.get(key);
            CurrentNum++;
            if(CurrentNum > MaxNumOfMessage)
            {
                CurrentNum = 0;
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
}
