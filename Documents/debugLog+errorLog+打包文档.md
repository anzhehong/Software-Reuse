# 软件复用管理文档

>2016.05.23

# 项目组成员

| 姓名        | 学生账号           | Github Id  |
| ------------- |:-------------:| -----:|
| 林思妙| 1352849 | [linsimiao](https://github.com/linsimiao) | 
|  康慧琳     | 1352871      |   [1352871](https://github.com/1352871)|
| 安哲宏 | 1352834 | [anzhehong](https://github.com/anzhehong) |
| 张嘉琦 | 1352863      |   [kobpko](https://github.com/kobpko)  |

***

## DebugLog功能

###关键代码

```java
   
    public static void DebugLog(String fileName, String contentMsg, String className, 
                                int lineNumber, String outPath) 
    {
        boolean flag = ReadJson.getBooleanConfig("DebugLogSwitch");
        contentMsg = className + ": " + lineNumber + "  " + contentMsg;
        if (flag) {
            PMManager.writeLog(fileName, contentMsg, outPath);
        }else {

        }
    }
```


###使用示例
```java
     PMManager.DebugLog(debugOutName,"日志内容",this.getClass().getName(),
                        ClassUtil.getLineNumber(),debugOutPath);
```



## ErrorLog功能

###关键代码

```java
   
    public static void ErrorLog(String fileName, String contentMsg, String className, 
                                int lineNumber, String outPath)
    {
        boolean flag = ReadJson.getBooleanConfig("ErrorLogSwitch");
        contentMsg = className + ": " + lineNumber + "  " + contentMsg;
        if (flag) {
            PMManager.writeLog(fileName, contentMsg, outPath);
        }else {

        }
    }
```

###使用示例
```java
    try {
      ...
          } catch (JMSException e1) {
              e1.printStackTrace();
              PMManager.ErrorLog(errorOutName, e1.toString(), this.getClass().getName(), 
                                 ClassUtil.getLineNumber(), errorOutPath);
            }
           
```


## 对DebufLog+ErrorLog功能进行配置：开启or关闭 

###核心代码

```java

//test.json文件中进行配置

"ErrorLogSwitch": true,
"ErrorLogPath" : "../Resources/out/ErrorLog/",
"DebugLogSwitch": true,
"DebugLogPath" : "../Resources/out/DebugLog/"
```
## 短周期打包功能

###关键代码

```java
   
    public static void LogDaily(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        Zip(ErrorLogPath,FirstZipErrorLogPath,df.format( new Date()));
        Zip(DebugLogPath,FirsrZipDebugLogPath,df.format( new Date()));
        Zip(NormalLogPath,FirstZipNormalLogPath,df.format( new Date()));

    }
```


###使用示例
```java
     Zip.LogDaily();
```

## 长周期打包功能

###关键代码

```java
   
     public  static void LogWeekly(){
        unZip(FirstZipErrorLogPath,FirstZipErrorLogPath);
        unZip(FirsrZipDebugLogPath,FirsrZipDebugLogPath);
        unZip(FirstZipNormalLogPath,FirstZipNormalLogPath);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        Zip(FirstZipErrorLogPath,SecondZipErrorLogPath,df.format(new Date()));
        Zip(FirsrZipDebugLogPath,SecondZipDebugLogPath,df.format(new Date()));
        Zip(FirstZipNormalLogPath,SecondZipNormalLogPath,df.format(new Date()));

    }
```


###使用示例
```java
     Zip.LogWeekly();
```
