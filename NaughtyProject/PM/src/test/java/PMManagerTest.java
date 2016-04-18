/**
 * Created by fowafolo
 * Date: 16/4/17
 * Time: 22:44
 */
public class PMManagerTest {
    @org.junit.Test
    public void write() throws Exception {
        PMManager pmManager = new PMManager("/Users/fowafolo/Desktop/1111/",1);
        pmManager.startRecord();
        pmManager.LogSuccess();
        pmManager.LogSuccess();
        pmManager.LogFail();
    }

}