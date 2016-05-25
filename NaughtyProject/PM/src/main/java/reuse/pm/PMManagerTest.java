package reuse.pm;

import org.junit.Test;
import reuse.cm.ReadJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by acer88 on 2016/5/25.
 */
public class PMManagerTest {

    private static String jsonPath =  "../Resources/test.json";

    @Test
    public void errorLog() throws Exception {
        String errorPath = new ReadJson(jsonPath).getStringConfig("ErrorLogPath") + File.separator;
       // System.out.println(errorPath);
        String errorName = "ErrorLog.txt";
        String errorMsg="errorTest";
        String className=this.getClass().getName();
        int lineNumber=100;
        PMManager.ErrorLog(errorName, errorMsg, className, lineNumber, errorPath);
        File file=new File(errorPath + "ErrorLog.txt");
        String ErrorLog = readLastLine(file);
        System.out.println(ErrorLog);
        String ErrorLog_=className + ": " + lineNumber + "  " + errorMsg+"\r\n";
        System.out.println(ErrorLog_);

     /*  if(ErrorLog.equals(ErrorLog_))
       {
           System.out.println("errorLog() successfully！");
       }
       else
       {
           System.out.println("errorlog() failed!");
       }
       */
    }


    @Test
    public void debugLog() throws Exception {
        String DebugPath = new ReadJson(jsonPath).getStringConfig("DebugLogPath") + File.separator;
        // System.out.println(errorPath);
        String DebugName = "DebugLog.txt";
        String DebugMsg="DebugTest";
        String className=this.getClass().getName();
        int lineNumber=99;
        PMManager.ErrorLog(DebugName, DebugMsg,className, lineNumber,DebugPath);
        File file=new File(DebugPath + "DebugLog.txt");
        String DebugLog = readLastLine(file);
        System.out.println(DebugLog);
        String DebugLog_=className + ": " + lineNumber + "  " + DebugMsg+"\r\n";
        System.out.println(DebugLog_);

     /* if(DebugLog.equals(DebugLog_))
       {
           System.out.println("DebugLog() successfully！");
       }
       else
       {
           System.out.println("Debuglog() failed!");
       }
       */
    }



    public static String readLastLine(File file) throws IOException {
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                return new String(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception ea) {
                    ea.printStackTrace();
                }
            }
        }
        return null;
    }
}