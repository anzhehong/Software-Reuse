# Communication Component Document

> This is the illustration for our Communication Component of Software Testing Course Project, SSE Tongji University, Spring 2016.

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

### Jar

You can download the `.jar` file [here](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0410_Communication-1.0-SNAPSHOT.jar).

## Usage


This utility encapsulates three files.
1. **MQFactory**

	The methods are made in static way, therefore you can use any of them without instantiation.
	```java
	/**
     * We set the default address of connection as "tcp://localhost:61616"
     * you can change the address using setAddress(String address)
     */
	 public static void setAddress(String address)

	/**
     * Return the a MessageConsumer created by QueueId
     */
	public static MessageConsumer getConsumer(String QueueID)

	/**
     * Return the a MessageProducer created by QueueId
     */
	public static MessageProducer getproducer(String QueueID)

	/**
     * Return the a publisher created by TopicID
     */
	public static MessageProducer getpublisher(String TopicID)

	/**
     * Return the a Subscribe created by TopicID
     */
	public static MessageConsumer getSubscriber(String TopicID)

	/**
     * Return the a Message
     */
	public static Message getMessage()
	```

2. **MQConnect**
	
	The methods are made in static way, therefore you can use any of them without instantiation.
	```java

	/**
     * Constructor of MQConnect
     */
	 public MQConnect(MessageProducer messageProducer, MessageConsumer messageConsumer) {
        this.messageProducer = messageProducer;
        this.messageConsumer = messageConsumer;
    }

    public MQConnect(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public MQConnect() {
    }

    public MQConnect(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

	/**
     * Send Message
     */
    public void sendMessage(Message message) throws JMSException {
        messageProducer.send(message);
    }

	/**
     * Add Message Handler
     */
    public void addMessageHandler(MessageListener messageListener) throws JMSException {
        messageConsumer.setMessageListener(messageListener);
    }

	/**
     * Get Producer or Consumer
     */	
	public MessageProducer getMessageProducer() {
        return messageProducer;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

	```

## Features

* Easy to understand and use.
* For more convenient use, all of the methods are made in static way.
* Make activeMq easy to use
* Do not to write so much code on create session,connection and so on. Save time.

## TODO

* More commen functions for more usages.
* Demo to illustrate.
* It can only support one brokerURL.