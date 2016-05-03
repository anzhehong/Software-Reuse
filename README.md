# Software-Reuse

> Source code, test code and docs for Software Reuse class 2016 Spring


# Introduction

This is an C-S Java Application to send messages between clients. Of course the messages sent by logged-in clients are validated firstly by the server and then forwarded to all of the valid clients.

One more important thing: the application is named as `Naught`.

> Additionally, I'd like to tell you something intresting about the name.
> 
> Cause many groups use `Netty` to accomplish the tasks, which we hadn't know it before they told us, and we choose `ActiveMQ` to be the main part of message middleware, in which way we will not care things about `Socket`. It may be a bad thing for learning the useful thing, `Socket`. Therefore we choose a name sounds like `Naughty` to make up. hhhhh...there seems to be nothing deserves laughing, right?...

# Directory

In the main Directory of our Git project, you will see two main directories: 

1. `NaughtyProject` is the main project directory, including all of the source code and test code.
2. `Documents` is the directory for project documents, written in Chinese, for better explanating our thoughts without misunderstanding...
3. `Disscussion` is the direcotry for in-class discussion homework, which is finished independently.
4. `Resources` is the directory for an example of `CM` component file. Anyone can modify a Json file by adding some properties in it and then they can use the properties by calling `ReadJson` 's functions. And `test.json` is our group's basic configuration file.
5. `Components` is the direcotory for documents and `.jar` file of our components.


# Members

| Name        | Student Id           | Github Id  |
| ------------- |:-------------:| -----:|
| 林思妙| 1352849 | [linsimiao](https://github.com/linsimiao) | 
|  康慧琳     | 1352871      |   [1352871](https://github.com/1352871)|
| 安哲宏 | 1352834 | [anzhehong](https://github.com/anzhehong) |
| 张嘉琦 | 1352863      |   [kobpko](https://github.com/kobpko)  |

**One more thing,** the components `.jar` file and document path are listed as follows.

# ***Component***

There are two ways for you to import our components into your project.

1. Maven
2. Jar File

You can refer to the document below for detail.

> **Notice**: Because installing methods of each component is very similar, we only make Utility Document in detail. If you're trying to use other components, you can refer to Utility Document.

|Component Name|Document Link|`.jar`File Download Link|
|:---:|:---:|:---:|
|Utility|[Utility Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Utilities/Utilities%20Component%20Document.md)|[Utility Jar](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0410_Utility-1.0-SNAPSHOT.jar)|
|CM|[CM Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/CM/CM%20Component%20Document.md)|[CM Jar](http://7xsf2g.com1.z0.glb.clouddn.com/jar0414_CM-1.0-SNAPSHOT-jar-with-dependencies.jar)|
|Communication|[Communication Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Communication/Communication%20Component%20Document.md)|[Communication Jar](http://7xsf2g.com1.z0.glb.clouddn.com/jar0414_Communication-1.0-SNAPSHOT-jar-with-dependencies.jar)|
|Database|[Database Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/Database/Database%20Component%20Document.md)|[Database Jar](http://7xsf2g.com1.z0.glb.clouddn.com/jar0414_Database-1.0-SNAPSHOT-jar-with-dependencies.jar)|
|License|[License Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/License/License%20Component%20Document.md)|[License Jar](http://7xsf2g.com1.z0.glb.clouddn.com/jar0414_License-1.0-SNAPSHOT-jar-with-dependencies.jar)|
|PM|[PM Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/PM/PM%20Component%20Document.md)|[PM Jar]((http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0417_PM-1.0-SNAPSHOT.jar)|


