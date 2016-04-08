# License Component Document：

> This is the illustration for our License Component of Software Testing Course Project, SSE Tongji University, Spring 2016.

<!--![](http://i.imgur.com/sBYNmyT.png)-->
<img src = "http://i.imgur.com/sBYNmyT.png" height = 400>
## Requirement

* Java JDK 1.6 +
* IntelliJ Idea 2014 +
* Maven 3 +


> Note: If your project is not based on Maven, the 3rd requirement can be omitted.


## Installing

Cause it is very similar to use all of our components, we make one of them in detail `Utilities`. You can refer to Utility Component Document [here](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Utilities/Utilities%20Component%20Document.md).

In other parts of our component documents, we will just add something different from using `Utilities`.

### Maven

```xml
<dependency>
	<groupId>tj.sse.reuse.group2</groupId>
	<artifactId>License</artifactId>
</dependency>
```

### Jar

You can totally refer to `Utility Component Document` [here](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Utilities/Utilities%20Component%20Document.md).

You can download [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0408_License-1.0-SNAPSHOT.jar).

## Usage

-   you should Instantiate the object that you would like to use.  

	```java
	FrequencyRestriction FrequencyRestriction = new FrequencyRestriction(num);
	```
	
> Note: The value of num confirm the limit of the component is "num" per second. 
	

-  Use the function "Check()" at the place which you want to limit.

	```java
	FrequencyRestriction.Check()
	```
-  The function will return false or true to tell you whether the action is limited or not.
	 
-  For the class `MaxNumOfMessage()`. it is same as what we talked before.

-  If you have **several** object that wanted to be limited.</br>
you can use **Multi-** just call the function `.addMap(name)` </br> 
And called `.CheckByKey(name)` to limit the certain object

###Example:
```java
FrequencyRestriction frequencyRestriction = new FrequencyRestriction(10)
for(int i = 0 ;i < 11;i++){
    System.out.print(i + " ");
    Thread.sleep(50);
    Boolean test = frequencyRestriction.Check();
    System.out.println(test);
}
```

> We call the function `Check()` for 11 times in one seconds,so the 11th output of `test` is false, The previous output is true.
> 
	`0 true`
	`1 true`
	`2 true`
	`3 true`
	`4 true`
	`5 true`
	`6 true`
	`7 true`
	`8 true`
	`9 true`
	`10 false`


## Features

* Easy to understand and use.
* For more convenient use, all of the methods are made in static way.

## TODO
           
* Demo to illustration.