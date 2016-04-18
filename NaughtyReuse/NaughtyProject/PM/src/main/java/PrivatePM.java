import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MSI on 2016/4/13.
 */
public class PrivatePM{

    private List<String> stringlist;

    public PrivatePM() {
        this.stringlist = new ArrayList<String>();
    }


    public void addkey(String key){
        stringlist.add(key);
    }

    public void Write(String fileName, String content, String outPath){
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(outPath + fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(stringtransfer(content)+"\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String stringtransfer(String input){
        String[] sourceStrArray=input.split(",",stringlist.size());
        String output= "";
        for(int i =0;i<sourceStrArray.length;i++){
            output = output + stringlist.get(i) + ":" + sourceStrArray[i] + "\t";
        }
        return output;
    }

    public static void main(String[] args) {
        PrivatePM privatePM = new PrivatePM();
        privatePM.addkey("name");
        privatePM.addkey("value");
        privatePM.addkey("grade");
        String a = privatePM.stringtransfer("qinbo,shabi,10");
        System.out.print(a);
    }
}
