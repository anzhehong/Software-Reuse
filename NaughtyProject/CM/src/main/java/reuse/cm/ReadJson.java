package reuse.cm;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ReadJson{

    static Map<String,String> configmap;

    public static String GetConfig(String str,String path){
        Gson gson = new Gson();
        FileInputStream configIn = null;
        configmap  = new HashMap<String, String>();

        try {
            configIn = new FileInputStream(path);
            configmap = gson.fromJson(IOUtils.toString(configIn), configmap.getClass());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(configIn);
        }
        Iterator<String> iter = configmap.keySet().iterator();
        while(iter.hasNext()){

            String key = iter.next();
            if(str.compareTo(key) == 0){

                return configmap.get(key);
            }

        }
        return null;
    }
    public static void main(String[] args){

    }
}