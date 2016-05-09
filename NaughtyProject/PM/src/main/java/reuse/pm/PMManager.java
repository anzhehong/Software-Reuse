package reuse.pm;

import com.sun.corba.se.spi.activation.Server;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
import reuse.cm.ReadJson;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * Created by MSI on 2016/3/28.
 */
public class PMManager {
    /**
     * 写文件
     * @param fileName 文件路径
     * @param content  内容
     */

    public static void Write(String fileName, String content, String outPath) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(outPath + fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content+"\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录登录记录，包括时间、client使用的username、是否成功
     * @param ClientName
     * @param result
     */
    public static void WriteLoginForClient(String ClientName , boolean result, String outPath){
        Date date = new Date();
        Write("Log.txt","Client: " + ClientName + " login " + result + " at " + date, outPath);
    }

    public static void main(String[] args) {

    }

}
