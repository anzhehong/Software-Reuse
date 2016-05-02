package reuse.pm;

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


    /**将client和server端输出的文件夹里所有没有被压缩过的文件压缩
     @param sourceFilePath client和server端输出的文件夹
     @param zipFilePath  压缩包文件夹
     @param fileName 压缩包文件名
     */

    public static boolean DailyZip(String sourceFilePath,String zipFilePath,String fileName){
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if(sourceFile.exists() == false){
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else {
            File[] sourceFiles = sourceFile.listFiles();
            int tmpCount = 0;
            for(int i = 0;i < sourceFiles.length;i++){
                if(sourceFiles[i].getName().charAt(0) != 'y')
                    tmpCount++;
            }
            if (null == sourceFiles || tmpCount == 0) {
                System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");

            } else {
                try {
                    File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
                    if (zipFile.exists()) {
                        System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                    } else {
                            fos = new FileOutputStream(zipFile);
                            zos = new ZipOutputStream(new BufferedOutputStream(fos));
                            byte[] bufs = new byte[1024 * 10];
                            for (int i = 0; i < sourceFiles.length; i++) {
                                //创建ZIP实体，并添加进压缩包
                                if (sourceFiles[i].getName().charAt(0) != 'y') {
                                    //更改文件名字
                                    ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                                    zos.putNextEntry(zipEntry);
                                    //读取待压缩的文件并写进压缩包里
                                    fis = new FileInputStream(sourceFiles[i]);
                                    bis = new BufferedInputStream(fis, 1024 * 10);
                                    int read = 0;
                                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                        zos.write(bufs, 0, read);
                                    }
                                    sourceFiles[i].renameTo(new File(sourceFilePath + "/y" + sourceFiles[i].getName()));
                                    flag = true;
                                }
                            }
                        }
                    }
                 catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } finally {
                    //关闭流
                    try {
                        if (null != bis) bis.close();
                        if (null != zos) zos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return flag;
    }


    //每周压缩
    public static boolean WeeklyZip(String sourceFilePath,String zipFilePath,String fileName){
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if(sourceFile.exists() == false){
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else {
            File[] sourceFiles = sourceFile.listFiles();
            int tmpCount = 0;
            for(int i = 0;i < sourceFiles.length;i++){
                if(sourceFiles[i].getName().charAt(0) != 'y')
                    tmpCount++;
            }
            if (null == sourceFiles || tmpCount == 0) {
                System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");

            } else {
                try {
                    File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
                    if (zipFile.exists()) {
                        System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            //创建ZIP实体，并添加进压缩包
                            if (sourceFiles[i].getName().charAt(0) != 'y') {
                                //更改文件名字
                                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                                zos.putNextEntry(zipEntry);
                                //读取待压缩的文件并写进压缩包里
                                fis = new FileInputStream(sourceFiles[i]);
                                bis = new BufferedInputStream(fis, 1024 * 10);
                                int read = 0;
                                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                    zos.write(bufs, 0, read);
                                }
                                sourceFiles[i].delete();
                                //sourceFiles[i].renameTo(new File(sourceFilePath + "/y" + sourceFiles[i].getName()));
                                flag = true;
                            }
                        }
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } finally {
                    //关闭流
                    try {
                        if (null != bis) bis.close();
                        if (null != zos) zos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return flag;
    }
   //每周解压
    public static void unZipFile(String zipFilename,String unzipPath){
        long startTime=System.currentTimeMillis();
        try {
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(zipFilename));
            BufferedInputStream Bin=new BufferedInputStream(Zin);
            File Fout=null;
            ZipEntry entry;
            try {
                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
                    Fout=new File(unzipPath,entry.getName());
                    if(!Fout.exists()){
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out=new FileOutputStream(Fout);
                    BufferedOutputStream Bout=new BufferedOutputStream(out);
                    int b;
                    while((b=Bin.read())!=-1){
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    System.out.println(Fout+"解压成功");
                }
                Bin.close();
                Zin.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");
    }

    //每周解压又重新压缩
    public  static void unzipAndzipWeekly(){
        ReadJson readJson = new ReadJson("/Users/Sophie/Software-Reuse/NaughtyProject/test.json");
        String zipDailyPath = readJson.getStringConfig("zipDailyPath");
        String tmpUnzippedPath = readJson.getStringConfig("tmpUnzippedPath");
        String zipWeeklyPath = readJson.getStringConfig("zipWeeklyPath");
        File file = new File(zipDailyPath);
        File[] files = file.listFiles();
        for(int i = 0;i < files.length;i++) {
            if(files[i].getName().charAt(0)!='y') {
                PMManager.unZipFile(zipDailyPath + File.separator + files[i].getName(), tmpUnzippedPath);
                files[i].renameTo(new File(zipDailyPath + File.separator + "y" + files[i].getName()));
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        PMManager.WeeklyZip(tmpUnzippedPath,zipWeeklyPath,df.format(new Date()));

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    //  unzipAndzipWeekly();
    }

}
