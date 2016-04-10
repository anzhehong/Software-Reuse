# Utilities Component Document

> This is the illustration for our Utilities Component of Software Testing Course Project, SSE Tongji University, Spring 2016.

## Requirement

* Java JDK 1.6 +
* IntelliJ Idea 2014 +
* Maven 3 +

> Note: If your project is not based on Maven, the 3rd requirement can be omitted.

## Installing

There are totally two methods to install our component.

### Maven

1. Firstly, you should downloand our maven module `Utilities` from our Github repository from [here](https://github.com/anzhehong/Software-Reuse).
2. Then, click `File` -> `New` -> `Module From Existing Sources` and then select the module you have just downloaded. After that, select `Maven` like the picture below:
	<img src = "http://7xsf2g.com1.z0.glb.clouddn.com/component_%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-04-08%2020.22.33.png" >

3. Click `Next` until the last one finished. 
4. Modify the imported module's `pom.xml` like:

	```xml
	<modelVersion>4.0.0</modelVersion>
    <artifactId>Utility</artifactId>
    <groupId>tj.sse.reuse.group2</groupId>
    <version>1.0-SNAPSHOT</version>
	```
	Yeah, it means you should delete the `<Parent>` tag and copy the `<groupId>` tag and `<version>` tag outside.
	
5. Modify your project's `pom.xml` file like:

	```xml
	<dependencies>
        <dependency>
            <groupId>tj.sse.reuse.group2</groupId>
            <artifactId>Utility</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
	```
	
6. Now you can use the utilities inside the module.

### Jar

1. Download the `Utility-1.0-SNAPSHOT.jar` file to your computer.
	
	You can easily download `Utility-1.0-SNAPSHOT.jar` from [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0410_Utility-1.0-SNAPSHOT.jar).
	
2. Import the `.jar` file to your project library.
	
	1. Open project structures in your project in IntelliJ Idea. 
	
	2. Import the java library Jar like the picture below.

		<img src = "http://7xsf2g.com1.z0.glb.clouddn.com/component_%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-04-08%2014.57.10.png" height = 300>
		
	3. Then you can use the utililies inside the jar.

## Usage

This utility encapsulates three files.

1. **DateUtil**
	
	The methods are made in static way, therefore you can use any of them without instantiation.
	
	```java
	/**
     * Return the string value of the current date with the default date format: "yyyy-MM-dd HH:mm:ss"
     */
    public static String getTodayStr()

    /**
     * Return the string value of the date parameter with the default date format: "yyyy-MM-dd HH:mm:ss"
     */
    public static String getStrByDefaultFormat(Date date)

    /**
     * Return the string value of the date parameter with the date format parameter
     */
    public static String getStrByCustomFormat(Date date, String pattern)
	```
	
	And we also make a sample for you.
	
	
	```java
	public class MainTest {
	    public static void main(String[] args) {
	        System.out.println(DateUtil.getTodayStr());
	        System.out.println(DateUtil.getStrByDefaultFormat(new Date()));
	        System.out.println(DateUtil.getStrByCustomFormat(new Date(), "hh:mm:ss/ MM dd yy"));
	    }
	}
	```

2. **EventController**

	This class is used to create a new event bus. Method is shown below:
	
	```java
	/**
     * Create a new EventBus
     */
    public static EventBus eventBus = new EventBus("controller");
    ```

3. **TimeUtil**

	This class is made to calculate two `Date` s' interval(in second unit).
	
	```java
	/**
     * calculate date1 and date2 's interval(in second unit).
     * @param date1
     * @param date2
     * @return
     */
    static public long getTimeInterval(Date date1, Date date2) 
	```

## Features

* Easy to understand and use.
* For more convenient use, all of the methods are made in static way.

## TODO

* More commen functions for more usages.
* Demo to illustrate.