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

Steps are very Similar to use `Utility`, but the `pom.xml` file should be different. You can refer to `Utility`, but do notice the difference of `artifactId` of each component.

### Jar

You can download [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0410_License-1.0-SNAPSHOT.jar).

## Usage

### 1. FrequencyRestriction

-   you should Instantiate the object that you would like to use.  

	```java
	FrequencyRestriction FrequencyRestriction = new FrequencyRestriction(num);
	```
	
> Note: The value of num confirm that the limit of the component is "num" per second. 
	

-  Use the function **Check()** at the place which you want to limit.

	```java
	FrequencyRestriction.Check()
	```
-  The function will return false or true to tell you whether the action is limited or not.

-  If you have **several** object that wanted to be limited.</br>
you can use **Multi-** just call the function `.addMap(name)` </br> 
And called `.CheckByKey(name)` to limit the certain object

### Example:

```java
FrequencyRestriction frequencyRestriction = new FrequencyRestriction(10);
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

### 2. MaxNumOfMessage
-   you should Instantiate the object that you would like to use.  

	```java
	MaxNumOfMessage maxNumOfMessage = new MaxNumOfMessage(num);
	```
	
> Note: The value of num confirm the max number of the component is num. 
	

-  Use the function **Check()** at the place which you want to limit.

	```java
	MaxNumOfMessage.Check()
	```
-  The function will return false or true to tell you whether the action is limited or not.

-  If you have **several** object that wanted to be limited.</br>
you can use **Multi-** just call the function `.addMap(name)` </br> 
And called `.CheckByKey(name)` to limit the certain object

### Example:

```java
MaxNumOfMessage maxNumOfMessage = new MaxNumOfMessage(10);
for(int i = 0 ;i < 11;i++){
    System.out.print(i + " ");
    Boolean test = frequencyRestriction.Check();
    System.out.println(test);
}
```

> We call the function `Check()` for 11 times ,so the 11th output of `test` is false, The previous output is true.
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

### 3. MultiFrequencyRestriction
-   you should Instantiate the object that you would like to use.  

	```java
	MultiFrequencyRestriction multiFrequencyRestriction = new MultiFrequencyRestriction(num);
	```
	
> Note: The value of num confirm that the limit of the component is "num" per second. 
	
-  Use the function **addMap(key)** to add an limiter .
 	```java
	multiFrequencyRestriction.addMap("first");
	```
-  Use the function **CheckCheckByKey(key)** at the place which you want to limit.

	```java
	FrequencyRestriction.CheckByKey("first")
	```
-  The function will return false or true to tell you whether the action is limited or not.

### Example:

```java
	MultiFrequencyRestriction multiFrequencyRestriction = new MultiFrequencyRestriction(10);
	multiFrequencyRestriction.addMap("first");
        for(int i = 0 ;i < 10;i++){
            System.out.print(i + " ");
            Thread.sleep(50);
            Boolean test = multiFrequencyRestriction.CheckByKey("first");
            System.out.println(test);           
        }

        System.out.print(11 + " ");
        Thread.sleep(50);
        Boolean test = multiFrequencyRestriction.CheckByKey("first");
        System.out.println(test);

        Boolean testError = multiFrequencyRestriction.CheckByKey("second");
        System.out.println(testError);
        

        Thread.sleep(1001);
        for(int i = 0 ;i < 20;i++){
            System.out.print(i + " ");
            Thread.sleep(200);
            Boolean testSuccess = multiFrequencyRestriction.CheckByKey("first");
            System.out.println(testSuccess);
       
        }
	
```

> We call the function `Check()` for 11 times ,so the 11th output of `test` is false, The previous output is true.
> 
	0 true
	1 true
	2 true
	3 true
	4 true
	5 true
	6 true
	7 true
	8 true
	9 true
	11 false	
	false
	12 true
	13 true
	14 true
	15 true
	16 true
	17 true
	18 true
	19 true
	20 true
	21 true
	22 true
	23 true
	24 true
	25 true
	26 true
	27 true
	28 true
	29 true
	30 true
	31 true

### 4. MultiMaxNumOfMessage
> You can get the usage of this function by the previous presentation.

## Features

* Easy to understand and use.
* Control serveral object at the same time.

## TODO
           
* Provide more constraints