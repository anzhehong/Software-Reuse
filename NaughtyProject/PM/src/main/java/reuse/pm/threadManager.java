package reuse.pm;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;

import reuse.cm.ReadJson;

/**
 * Created by Sophie on 16/4/25.
 */
public abstract class threadManager {


        protected static final long NEVER = Long.MAX_VALUE;

        // 定义一个线程锁，保证当前只有一个工作在操作中
        private final Object lock = new Object();

        // 定义一个Thread变量
        private Thread thread;

        // 控制线程循环的开关
        private boolean active = true;

        // 定义一个毫秒级的时间变量，指示何时执行下一个操作
        private long nextTime;


        /**
         * 定义个一个抽象的方法用来获取下一个执行操作的时间，可使用NEVER
         */
        protected abstract long getNextTime() throws ParseException;


        /**
         * 定义一个抽象的方法，让子类来定义具体的工作过程
         */
        protected abstract void executeWork() ;

        protected String getName()
        {
            return getClass().getName();
        }

        /**
         * 启动线程
         */
        public void start()
        {
            thread = new Thread(new Runnable()
            {
                public void run()
                {
                    runInternal();
                }
            }, getName());
            thread.start();
        }

        /**
         * 强迫停止线程，跳出for循环
         */
        public void stop() throws InterruptedException
        {
            synchronized (lock)
            {
                active = false;
                lock.notify();
            }
            thread.join();
        }

        /**
         * 此方法可以在任何时候激活当前线程，让线程进入工作执行环节
         */
        public void workAdded(long time)
        {
            synchronized (lock)
            {
                if (time < nextTime)
                {
                    // 立刻激活线程工作继续运行
                    lock.notify();
                }
            }
        }

        /**
         * 线程监测控制逻辑部分
         */
        private void runInternal()
        {
            // 无限循环
            for (;;)
            {
                // 该过程忽略了所有的Exception，以保证线程不会因此而中断
                try
                {

                    synchronized (lock)
                    {
                        nextTime = getNextTime();

                        // 获得时间区间，即要等待的时间段
                        long interval = nextTime - System.currentTimeMillis();
                         if (interval>0 )
                        {
                            try
                            {
                                lock.wait(interval);
                            }
                            catch (InterruptedException e)
                            {
                                // 忽略此Exception
                            }
                        }
                        // 如果active为false，强制中断
                        if (!active)
                        {
                            break;
                        }
                    }
                    // 执行具体的工作
                    executeWork();

                }
                catch (Throwable t)
                {
                    try
                    {
                        Thread.sleep(10000);
                    }
                    catch (InterruptedException ie)
                    {
                        // 忽略此Exception
                    }
                }
            }
        }


}

/**
 * 线程的具体实现
 */
 class MyDataGenerator extends threadManager  {
    protected void executeWork()  {

         System.out.println("Execute work ...");
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String fileName = df.format(new Date());

         ReadJson readJson = new ReadJson("/Users/Sophie/Software-Reuse/NaughtyProject/test.json");
         String sourceFilePath = (readJson.getStringConfig("sourcePath"));
         String zipFilePath = (readJson.getStringConfig("zipPath"));

        boolean flag = PMManager.fileToZip(sourceFilePath, zipFilePath, fileName);
        if(flag){
            System.out.println("文件打包成功!");
        }else{
            System.out.println("文件打包失败!");
        }

    }



    protected long getNextTime()throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //return System.currentTimeMillis()+24*60*60*1000L;
        return System.currentTimeMillis()+10000L;
    }
     public static void main(String argv[]) {
         MyDataGenerator generator = new MyDataGenerator();
         generator.start();
     }


 }

