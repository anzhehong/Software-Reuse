import javax.jms.Message;

/**
 * Created by fowafolo
 * Date: 16/3/27
 * Time: 21:46
 */
public class InterfaceEvent {
    private String str;
    private Message message;
    public String getStr() {
        return str;
    }
    public Message getMessage(){return message;}
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public InterfaceEvent(Message message, String str) {
        this.message = message;
        this.str = str;
    }

    public InterfaceEvent(String str) {
        this.str = str;
    }

    public InterfaceEvent(String userName, String str) {
        this.userName = userName;
        this.str = str;
    }

    /**
     * Constructure 用于Client向界面传信息
     * @param str
     * @param message
     */
    public InterfaceEvent(String str, Message message) {
        this.str = str;
        this.message = message;
    }
}
