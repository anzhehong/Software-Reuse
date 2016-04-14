package reuse.license;

/**
 * Created by MSI on 2016/4/6.
 */
public class MaxNumOfMessage implements MessageRestriction {

    private int MaxNumOfMessage;
    private int CurrentNum;

    public int getMaxNumOfMessage() {
        return MaxNumOfMessage;
    }

    public MaxNumOfMessage(int maxNumOfMessage) {
        MaxNumOfMessage = maxNumOfMessage;

        CurrentNum = 0;
    }

    public void reload(){
        CurrentNum = 0;
    }


    @Override
    public boolean Check() {
        CurrentNum++;
        if(CurrentNum > MaxNumOfMessage)
        {
            reload();
            return false;
        }
        return true;
    }
}