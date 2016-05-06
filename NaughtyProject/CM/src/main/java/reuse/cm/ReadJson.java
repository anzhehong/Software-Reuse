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

    static  Map<String,Object> configmap;

    public ReadJson(String str) {
        Gson gson = new Gson();
        FileInputStream configIn = null;
        configmap  = new HashMap<String, Object>();
        try {
            configIn = new FileInputStream(str);
            configmap = gson.fromJson(IOUtils.toString(configIn), configmap.getClass());
        } catch (JsonSyntaxException e) {
            // e.printStackTrace();
            System.out.println("file format error...");             //文件格式有误
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("file opening error...");           //文件读取有误
        } finally {
            IOUtils.closeQuietly(configIn);
        }
    }

    public static String getStringConfig(String str) throws RuntimeException{
        Iterator<String> iter = configmap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (str.compareTo(key) == 0) {
                return configmap.get(key).toString();
            }
        }
        throw new RuntimeException("Sorry there is no corresponding value...");       //找不到对应的值

    }


    public static int getIntConfig(String str) throws RuntimeException{
        Iterator<String> iter = configmap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if(str.compareTo(key) == 0){
                try{
                    return (int)(Double.parseDouble(configmap.get(key).toString()));}
                catch (NumberFormatException e){
                    System.out.println("maybe the type you defined is not int,please have a check...");       //找到值但是不可以转化为int
                }
            }
        }
        throw new RuntimeException("Sorry there is no corresponding value...");
    }

    public static long getLongConfig(String str) throws RuntimeException{
        Iterator<String> iter = configmap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if(str.compareTo(key) == 0){
                try{
                    return (long)(Double.parseDouble(configmap.get(key).toString()));}
                catch (NumberFormatException e){
                    System.out.println("maybe the type you defined is not int,please have a check...");       //找到值但是不可以转化为int
                }
            }
        }
        throw new RuntimeException("Sorry there is no corresponding value...");
    }
    public static double getDoubleConfig(String str) throws RuntimeException{
        Iterator<String> iter =  configmap.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if(str.compareTo(key) == 0){
                try{
                    return Double.parseDouble(configmap.get(key).toString());}
                catch (NumberFormatException e){
                    System.out.println("maybe the type you defined is not double,please have a check...");      //找到值但是不可以转化为double
                }
            }
        }

        throw new RuntimeException("Sorry there is no corresponding value...");
    }



    public static boolean getBooleanConfig(String str) throws RuntimeException{
        Iterator<String> iter = configmap.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            if(str.compareTo(key) == 0){
                if(configmap.get(key).toString().compareTo("true") == 0){
                    return true;
                }
                else  if(configmap.get(key).toString().compareTo("false") == 0){
                    return  false;
                }
                else throw new RuntimeException("maybe the type you defined is not boolean,please have a check...\"");      //找到值但不是boolean，这里用boolean.parseToBoolean的话除了“true”其他值都会返回“false”，所以用自己的判断。
            }
        }
        throw new RuntimeException("Sorry there is no corresponding value...");
    }



    public static void main(String[] args){
        System.out.println(new ReadJson("../Resources/test.json").getStringConfig("mqHost"));
    }
}