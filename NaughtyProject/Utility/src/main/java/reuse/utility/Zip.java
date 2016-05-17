package reuse.utility;


import reuse.cm.ReadJson;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Sophie on 16/5/9.
 */
public class Zip {

    static public String jsonPath = "../Resources/test.json";
    static public ReadJson readJson = new ReadJson(jsonPath);
    static public String sourcePath = readJson.getStringConfig("sourcePath");
    static public String zipDailyPath = readJson.getStringConfig("zipDailyPath");
    static public String zipWeeklyPath = readJson.getStringConfig("zipWeeklyPath");

    /**将client和server端输出的文件夹里所有没有被压缩过的文件压缩
     @param sourceFilePath client和server端输出的文件夹
     @param zipFilePath  压缩包文件夹
     @param fileName 压缩包文件名
     */



    //压缩
    public static boolean Zip(String sourceFilePath,String zipFilePath,String fileName){
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
            if (null == sourceFiles || sourceFiles.length == 0) {
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
                            if(sourceFiles[i].isDirectory()){
//                                     Zip(sourceFilePath+File.separator+sourceFiles[i].getName(),zipFilePath,sourceFiles[i].getName());
//                                    sourceFiles[i].delete();
                                File[] singlefiles = sourceFiles[i].listFiles();
                                for(int k = 0 ;k < singlefiles.length;k++){
                                    ZipEntry zipEntry = new ZipEntry(singlefiles[k].getName());
                                    zos.putNextEntry(zipEntry);
                                    fis = new FileInputStream(singlefiles[k]);
                                    bis = new BufferedInputStream(fis, 1024 * 10);
                                    int read = 0;
                                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                        zos.write(bufs, 0, read);
                                    }
                                    singlefiles[k].delete();
                                    flag = true;

                                }
                                sourceFiles[i].delete();
                            }
                            else{
                                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                                zos.putNextEntry(zipEntry);
                                fis = new FileInputStream(sourceFiles[i]);
                                bis = new BufferedInputStream(fis, 1024 * 10);
                                int read = 0;
                                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                    zos.write(bufs, 0, read);
                                }
                                sourceFiles[i].delete();
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

    //解压
    public static void unZip(String sourceFilePath,String unzipFilePath) {
        long startTime = System.currentTimeMillis();
        File[] sourceFiles = new File(sourceFilePath).listFiles();
        System.out.println(sourceFiles.length);
        for (int j = 0;j < sourceFiles.length;j++){
            System.out.println(sourceFiles[j].getName());
        }
        if (sourceFiles.length > 0) {
            try {
                for(int i = 0;i < sourceFiles.length;i++){
                    ZipInputStream Zin = new ZipInputStream(new FileInputStream(sourceFilePath+File.separator+sourceFiles[i].getName()));
                    BufferedInputStream Bin = new BufferedInputStream(Zin);
                    File Fout = null;
                    ZipEntry entry;
                    try {
                        while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                            Fout = new File(unzipFilePath, entry.getName());
                            if (!Fout.exists()) {
                                (new File(Fout.getParent())).mkdirs();
                            }
                            FileOutputStream out = new FileOutputStream(Fout);
                            BufferedOutputStream Bout = new BufferedOutputStream(out);
                            int b;
                            while ((b = Bin.read()) != -1) {
                                Bout.write(b);
                            }
                            Bout.close();
                            out.close();
                            System.out.println(Fout + "解压成功");
                            sourceFiles[i].delete();
                        }
                        Bin.close();
                        Zin.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("耗费时间： " + (endTime - startTime) + " ms");
        }
    }

    //小周期压缩
    public static void zipDaily(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        Zip(sourcePath,zipDailyPath,df.format( new Date()));

    }

    //大周期压缩
    public  static void zipWeekly(){
        unZip(zipDailyPath,zipDailyPath);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        Zip(zipDailyPath,zipWeeklyPath,df.format(new Date()));

    }

    public static long getFileLength(String filename){
        File file = new File(filename);
        return file.length();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // zipDaily(); unZip1(zipDailyPath,zipDailyPath);
//        Zip(zipDailyPath,zipWeeklyPath,"h");
        // zipWeekly();
        //  unzipAndzipWeekly();
//        ReadJson readJson = new ReadJson("../Resources/test.json");
//        String path = readJson.getStringConfig("sourcePath");
//        File f = new File(path,"ha");
//        f.mkdir();
//        PMManager.Write("client","ji",(f+File.separator));

        // System.out.println(tef.getBytes().length);
        // System.out.println(getFileLength(str));
    }

}
