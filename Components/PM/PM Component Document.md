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

Steps are very Similar to use `Utility`, but the `pom.xml` file should be different. You can refer to `Utility`, but do notice the difference of `artifactId` of each component.

The dependency is as follows.
```xml
<dependency>
  <groupId>tj.sse.group2.reuse</groupId>
  <artifactId>PM</artifactId>
  <version>1.1.0</version>
  <classifier>dependencies</classifier>
</dependency>
```

### Jar

You can download the `.jar` file [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0417_PM-1.0-SNAPSHOT.jar).


## Usage
This PM encapsulates only one file.

1. **PMManager**
	
	First, you should do the instantiation.
	
	```java
     /**
     * instantiation
     * @param _filePath  
     * @param _delayMinute 
     */
    PMManager pmManager = new PMManager(String _filePath,int _delayMinute);
  
	```
	Second,start the Manager

	```java
    public void startRecord( )
  
	```
	Third, change the amount of the record.
	```java
	/**
     * Log in successfully
     */
    public void LogSuccess()

	/**
     * Log in failed
     */
    public void LogFail()
  
	```
	Besides,we can change the filepath.
	
	```
	/**
	 * reset the filepath
	 * param filepath
	 */
	public static void setFilepath(String filepath)
	```

## Example
Here is the example.
1. **PMManager**
	```java
	PMManager pmManager = new PMManager("/Users/fowafolo/Desktop/1111/",1);
    pmManager.startRecord();
    pmManager.LogSuccess();
    pmManager.LogSuccess();
    pmManager.LogFail();
	```	
	
## Features

* Easy to understand and use.
* Create the file on its own.

## TODO

* More commen functions for more usages.
* Demo to illustrate.
