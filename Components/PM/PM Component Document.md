# PM Component Document

> This is the illustration for our PM Component of Software Testing Course Project, SSE Tongji University, Spring 2016.

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

You can download the `.jar` file [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0417_PM-1.0-SNAPSHOT.jar).

## Usage
This PM encapsulates only one file.

1. **PMManager**
	
	The methods are made in static way, therefore you can use any of them without instantiation.
	
	```java
     /**
     * writes the content to the file specified
     * @param fileName 
     * @param content 
     * @param outPath 
     */
    public static void Write(String fileName, String content, String outPath);
  
	```
	
	```java
    /**
     * Noting the log records,including time,the username of client and whether success
     * @param ClientName
     * @param result
     */
    public static void WriteLoginForClient(String ClientName , boolean result, String outPath);
  
	```
	
	
	

## Features

* Easy to understand and use.
* For more convenient use, all of the methods are made in static way.

## TODO

* More commen functions for more usages.
* Demo to illustrate.
