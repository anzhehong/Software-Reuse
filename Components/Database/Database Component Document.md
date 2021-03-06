# Database Component Document

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

Steps are very Similar to use `Utility`, but the `pom.xml` file should be different. You can refer to `Utility`, but do notice the difference of `artifactId` of each component.

The dependency is as follows.
```xml
<dependency>
  <groupId>tj.sse.group2.reuse</groupId>
  <artifactId>Database</artifactId>
  <version>1.1.0</version>
  <classifier>dependencies</classifier>
</dependency>
```

### Jar

You can download the `.jar` file [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar0414_Database-1.0-SNAPSHOT-jar-with-dependencies.jar).

## Usage

This Database component encapsulates many files, but you just need to know `DBAPI`.

1. CheckPassword

	This API is used to check whether the username and password input by clients are right. 
	
	```java
	/**
     * check login permitted or not
     * @param username
     * @param password
     * @return  if true: permitted; 
     * 		    if false: prohibitted.
     */
    static public boolean CheckPassword(String username,String password)
	```

2. RegisterUser

	This API is used to check whether register successfully.
	
	```java
	/**
     * Register
     * @param username
     * @param pwd
     * @return  if true: successfully; 
     * 		    if false: unsuccessfully.
     */
    static public boolean RegisterUser(String username,String pwd)
	```


3. Entity

	Of course, the entity `User` should be known by you.

	```java
	private int id; //PK
	private String userName;
	private String userPassword;
	```	

	If you wanna test with existing users, you can try:
	> username: abc		password: abc
	> 
	> or
	> 
	> username: cba		password: cba

4. One more thing

	One more thing you need to know. 
	
	Our database is based on `Aliyun Database Service`, it means you should connect to internet before using this component.

## Features

* Easy to understand and use.
* For more convenient use, all of the methods are made in static way.

## TODO

* More commen functions for more usages.
* Demo to illustrate.



# Update

> 2016.05.03

For this week's homework, we modified the DBAPI by adding a new API into DBAPI so that we can get the user's group id while logged in successfully.

**Usage**

```java
/**
 * new interface for checking the password and username and return the group id of the user if exists
 * @param username
 * @param password
 * @return if log-in fails, error will be true and errorMsg will be returned; On the contrary, error will be false and groupId will be returned.
 */
static public Map<String, Object> checkPasswordAndGetGroup(String username, String password)
```

**Example**

```java
Map<String, Object> dbResult = DBAPI.checkPasswordAndGetGroup(userName, userPassword);
boolean flag = ((Boolean)dbResult.get("error")).booleanValue();

privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName), MQFactory.getConsumer("CS_" + userName));
if (!flag) {
    sendQueue(!flag, privateConnect, dbResult.get("groupId"));
}else {
    sendQueue(!flag, privateConnect, dbResult.get("errorMsg"));
}
```