package Client;

import com.Util.WriteLog;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/** 
* WriteLog Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 29, 2016</pre> 
* @version 1.0 
*/ 
public class WriteLogTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: write(String fileName, String content) 
* 
*/ 
@Ignore
public void testWrite() throws Exception { 
//TODO: Test goes here...
   //向文件输入一行数据，测试输入数据与文件最后一行数据是否相同；
    String txt = "testMeAgain";
    FileReader fr = new FileReader("testWrite.txt");
    WriteLog.write("testWrite.txt", txt);
    String lastLine= null;

    BufferedReader br = new BufferedReader(fr);
    String str = null;
    while ((str = br.readLine()) != null) {
        lastLine = str;
    }
    br.close();
    fr.close();
    assertEquals(txt,lastLine);

 // 打开一个随机访问文件流，按读写方式

} 

/** 
* 
* Method: writeLoginForClient(String ClientName, boolean result) 
* 
*/ 
@Test
public void testWriteLoginForClient() throws Exception { 
//TODO: Test goes here...
    Date date = new Date();
    String ClientName = "abc";
    boolean result = true;
    WriteLog.writeLoginForClient(ClientName, result);

    FileReader fr = new FileReader("Log.txt");
    String lastLine= null;
    BufferedReader br = new BufferedReader(fr);
    String str = null;
    while ((str = br.readLine()) != null) {
        lastLine = str;
    }
    br.close();
    fr.close();
    assertEquals("Log.txt", "Client: " + ClientName + " login " + result + " at " + date,lastLine);

} 

/** 
* 
* Method: createNewFile(String name) 
* 
*/ 
@Test
public void testCreateNewFile() throws Exception { 
//TODO: Test goes here... 
} 


} 
