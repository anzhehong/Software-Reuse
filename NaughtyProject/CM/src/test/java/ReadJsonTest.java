import org.junit.Test;
import reuse.cm.ReadJson;

/**
 * Created by fowafolo
 * Date: 16/4/7
 * Time: 22:26
 */
public class ReadJsonTest {

    @Test
    public void getConfig() throws Exception {
        System.out.println(ReadJson.GetConfig("id", "/Users/fowafolo/Desktop/test.json"));
    }
}