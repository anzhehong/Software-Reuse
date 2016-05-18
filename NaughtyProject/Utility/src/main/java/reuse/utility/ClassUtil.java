package reuse.utility;

/**
 * Created by fowafolo
 * Date: 16/5/18
 * Time: 17:08
 */
public class ClassUtil {

    static public int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
}
