# CM Component Document

> This is the illustration for our Utilities Component of Software Testing Course Project, SSE Tongji University, Spring 2016.

## Requirement

* Java JDK 1.6 +
* IntelliJ Idea 2014 +
* Maven 3 +

> Note: If your project is not based on Maven, the 3rd requirement can be omitted.

## Installing

There are totally two methods to install our component.

Cause it is very similar to use all of our components, we make one of them in detail `Utilities`. You can refer to Utility Component Document [here](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Utilities/Utilities%20Component%20Document.md).

### Maven

Steps are very Similar to use `Utility`, but the `pom.xml` file should be different. You can refer to `Utility` and notice the difference such as `artifactId`.

### Jar

You can download the `.jar` file [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0408_CM-1.0-SNAPSHOT.jar).

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
