# CM Component Document

> This is the illustration for our CM Component of Software Testing Course Project, SSE Tongji University, Spring 2016.

## Requirement

* Java JDK 1.6 +
* IntelliJ Idea 2014 +
* Maven 3 +

> Note: If your project is not based on Maven, the 3rd requirement can be omitted.

## Installing

There are totally two methods to install our component.

Cause it is very similar to use all of our components, we make one of them in detail `Utilities`. You can refer to Utility Component Document [here](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Utilities/Utilities%20Component%20Document.md).

### Maven

Steps are very Similar to use `Utility`, but the `pom.xml` file should be different. You can refer to `Utility`, but do notice the difference of `artifactId` of each component.

The dependency is as follows.
```xml
<dependency>
  <groupId>tj.sse.group2.reuse</groupId>
  <artifactId>CM</artifactId>
  <version>1.1.0</version>
  <classifier>dependencies</classifier>
</dependency>
```

### Jar

You can download the `.jar` file [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar0414_CM-1.0-SNAPSHOT-jar-with-dependencies.jar).

## Usage

This CM encapsulates only one file.

1. **ReadJson**
	
	The methods are made in static way, therefore you can use any of them without instantiation.
	
	```java
	/**
     * 1.Return the configuration value of ``string`` type provided the key value and the path of the config file.
     If there is no corresponding value,an exception will be thrown to inform the user.
     * 2.The args ``path`` should be a full path(absolute path).
     */
    
    static String GetStringConfig(String str,String path);
  
	```

	```java
	/**
     * 1.Return the configuration value of ``int`` type provided the key value and the path of the config file.
     If there is no corresponding value or the corresponding value is not an int,an exception will be thrown to inform the user.
     * 2.The args ``path`` should be a full path(absolute path).
     */
    
    static String GetIntConfig(String str,String path);
  
	```

	```java
	/**
     * 1.Return the configuration value of ``double`` type provided the key value and the path of the config file.
     If there is no corresponding value or the corresponding value is not a double,an exception will be thrown to inform the user.
     * 2.The args ``path`` should be a full path(absolute path).
     */
    
    static String GetDoubleConfig(String str,String path);
  
	```

	```java
	/**
     * 1.Return the configuration value of ``boolean`` type provided the key value and the path of the config file.
     If there is no corresponding value or the corresponding value is not a boolean,an exception will be thrown to inform the user.
     * 2.The args ``path`` should be a full path(absolute path).
     */
    
    static String GetBooleanConfig(String str,String path);
  
	```



	And we also make a sample for you.

	```java
	/**
	* 1.We provide a json file in the local path "/Users/fowafolo/Desktop/test.json",and it contains ``{"host":"localhost:8080","id":3,"price":98.99,"listen":false}''.
	* 2.As a result,``localhost:8080``、``3``、``98.99``、``false``will be printed out.
	*/
	
	public class ReadJsonTest {
	    @Test
	    public void GetStringConfig() throws Exception {
	        System.out.println(ReadJson.GetStringConfig("host", "/Users/fowafolo/Desktop/test.json"));
	        System.out.println(ReadJson.GetIntConfig("id", "/Users/fowafolo/Desktop/test.json"));
	        System.out.println(ReadJson.GetDoubleConfig("price", "/Users/fowafolo/Desktop/test.json"));
	        System.out.println(ReadJson.GetBooleanConfig("listen", "/Users/fowafolo/Desktop/test.json"));
	    }
	}


	```





## Features

* Easy to understand and use.
* For more convenient use, all of the methods are made in static way.

## TODO

* More commen functions for more usages.
* Demo to illustrate.
