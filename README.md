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
> This directory includes the following files: 
> 
|Name|URL|
|---|---|
|测试文档|[测试文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E6%B5%8B%E8%AF%95%E6%96%87%E6%A1%A3.md)|
|程序文档|[程序文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E7%A8%8B%E5%BA%8F%E6%96%87%E6%A1%A3.md)|
|复用文档|[复用文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E5%A4%8D%E7%94%A8%E6%96%87%E6%A1%A3.md)|
|软件复用管理文档|[软件复用管理文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E8%BD%AF%E4%BB%B6%E5%A4%8D%E7%94%A8%E7%AE%A1%E7%90%86%E6%96%87%E6%A1%A3.md)|
|构件使用文档|[构件使用文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E6%9E%84%E4%BB%B6%E4%BD%BF%E7%94%A8%E6%96%87%E6%A1%A3.md)|
|构件选择文档|[构件选择文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E6%9E%84%E4%BB%B6%E9%80%89%E6%8B%A9%E6%96%87%E6%A1%A3.md)|
|本组构件反馈跟踪|[本组构件反馈跟踪](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E6%9C%AC%E7%BB%84%E6%9E%84%E4%BB%B6%E5%8F%8D%E9%A6%88%E8%B7%9F%E8%B8%AA.md)|
|保存和打包消息文件文档|[保存和打包消息文件文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E4%BF%9D%E5%AD%98%E5%92%8C%E6%89%93%E5%8C%85%E6%B6%88%E6%81%AF%E6%96%87%E4%BB%B6%E6%96%87%E6%A1%A3.md)|
|加密解密文档|[加密解密文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E5%8A%A0%E5%AF%86%E8%A7%A3%E5%AF%86.md)|
|软件复用分组发送更新文档|[软件复用分组发送更新文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E8%BD%AF%E4%BB%B6%E5%A4%8D%E7%94%A8%E5%88%86%E7%BB%84%E5%8F%91%E9%80%81%E6%96%87%E6%A1%A3.md)|
|输出文件控制+归档文件加密复用文档|[输出文件控制+归档文件加密复用文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E8%BE%93%E5%87%BA%E6%96%87%E4%BB%B6%E6%8E%A7%E5%88%B6%2B%E5%BD%92%E6%A1%A3%E6%96%87%E4%BB%B6%E5%8A%A0%E5%AF%86%E5%A4%8D%E7%94%A8%E6%96%87%E6%A1%A3)|
|持久化消息+显示同组成员列表文档|[持久化消息+显示同组成员列表文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E6%8C%81%E4%B9%85%E5%8C%96%E6%B6%88%E6%81%AF%2B%E6%98%BE%E7%A4%BA%E5%90%8C%E7%BB%84%E6%88%90%E5%91%98%E5%88%97%E8%A1%A8.md)|
|debugLog+errorLog+打包文档|[debugLog+errorLog+打包文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/debugLog%2BerrorLog%2B%E6%89%93%E5%8C%85%E6%96%87%E6%A1%A3.md)
|拆分Server复用文档|[拆分Server复用文档](https://github.com/anzhehong/Software-Reuse/blob/master/Documents/%E6%8B%86%E5%88%86Server%E5%A4%8D%E7%94%A8%E6%96%87%E6%A1%A3.md)
	
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
|PM|[PM Document](https://github.com/anzhehong/Software-Reuse/blob/master/Components/PM/PM%20Component%20Document.md)|[PM Jar](http://7xsf2g.com1.z0.glb.clouddn.com/jar_version0417_PM-1.0-SNAPSHOT.jar)|


