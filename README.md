# HttpJsonToHttpJson

This is JSON to JSON conversion REST API implemented in IIB 10.0.0.4 to demonstrate a common framework. This framework is desinged with careful consideration of 12 factors for creating cloud ready next generation architecture and also will perfectly integrate with DevOps processes in order to increase development and deployment efficiency. This framework includes:
<<<<<<< HEAD
- **Common ProcessingCore Framework** to bring in consistency of rules across applications. This includes:
 - **Cache Manager** - Framework to control what goes in the cache and to leverage the global cache of the IIB.
 - Tranformation Utils and interfaces - Control, manage and helper classes for the Transformation Business Logic
 - **IIB Java Compute Framework** - Common Java Compute fraemwork to simplify developer code and bring consistency across message flows. Sample Java Computes with and without Cache are included in the Application project - help you extend and implement your message flows. The Java computes are expected merely gather the data from input message, cache etc and pass on to the transformation framework.
 - We have also provided sample data source for cache and sample cacheable pojo objects - an exmaple of how to imeplement and add to the caching freamework
- We developed an **extensive Unit Test Framework** to trigger test driven development. This includes:
 - Unit TestCases for Transformation Business Logic
 - Automation framework to test message flow nodes - Automated Unit Test Cases corresponding to flow exerciser UI. We implemented this ground breaking automation framework to integrate the flow tests with DevOps. These test cases help observe the message flowing through the message flow and perform checks after each node traversal. 
- **Logging and monitoring framework**
 - Monitoring - enables logs and business transaction monitoring on message flows (We would like you to use this Out of the box feature of IIB to gain advantages of business transaction monitoring done using MQ and Database schema). Although the set up is fairely complex, we did all the hardwork to figure out exact steps for you and note that this needs to be done only once.
 - Exception Handling (TBD)
 - Logging (TBD) - Use log4j in order to debug, develop and monitor details the business logic. Feed to splunk or queue as and when needed by tweaking log4j properties. 
- **Transformation Framework**
 - The transformation framework is designed to make transformation, independent of an ESB context, they can be independently tested - done entirely in Java so that transformation from any format to any other format such as JSON to XML, Plain Text to JSON, XML to Pojo etc. are abosolutely easy, developer controlled within Java code and fully test-automated (Sample Transformation Business logic Code with and without cache provided)
 - User defined variables - an example of how we would like you to consume the user defined variables in this fraemwork
 - Pattern for calling another REST API such as ZOSCOnnect - Performs transformation before calling a a REST API, Execute a REST API, and then perform transformatino on the result 
- **Build Framework**
 - Github repository source code management and bring all related projects under one umbrella
 - The Projects are places in flat structure to comply with IIB norms as well as simplicity of the code management. 
 - We chose to create a self contained bar file that can run in its own classloader. Gives advantages of Java Isolation of IIB 10
 - The Java projects such as Business Logic and FLow Test frameworks are maven projects that integrate well with DevOps and produce jar files that can be tested in an automated manner before deploy.



### How to import the project in IIB
1. In IIB toolkit, goto Window -> open perspective -> Git. If you dont see Git Repositories view, then goto Window -> Show view -> Git Repositories

2. In your git repositories view select -> **clone a git repository** -> Enter https://github.com/sanketsw/JsonJsonApplication.git and credentials -> After import is finished, right click on the repository and **import projects** -> import as exisitng projects -> select all projects.

3. Do the above step for the ProcessingCore repository as well. https://github.com/sanketsw/ProcessingCore.git
 
4. you may have to run maven clean package install on **ProcessingCore** project (right click pom.xml -> run as maven build-> enter goal as `clean package install`)

5. Right click and refresh the projects. If dependencies in Java project are not resolved, goto Project -> clean. Deploy the IIB application to default node.
=======
- **Common ProcessingCore Framework** (https://github.com/sanketsw/ProcessingCore.git) to bring in consistency of rules across applications. This includes:
 - **Cache Manager** - Framework to control what goes in the cache and to leverage the global cache of the IIB.
 - Tranformation Utils and interfaces - Control, manage and helper classes for the Transformation Business Logic
 - **IIB Java Compute Framework** - Common Java Compute fraemwork to simplify developer code and bring consistency across message flows. Sample Java Computes with and without Cache are included in the Application project - help you extend and implement your message flows. The Java computes are expected merely gather the data from input message, cache etc and pass on to the transformation framework.
 - We have also provided sample data source for cache and sample cacheable pojo objects - an exmaple of how to imeplement and add to the caching freamework
- We developed an **extensive Unit Test Framework** to trigger test driven development. This includes:
 - Unit TestCases for Transformation Business Logic
 - Automation framework to test message flow nodes - Automated Unit Test Cases corresponding to flow exerciser UI. We implemented this ground breaking automation framework to integrate the flow tests with DevOps. These test cases help observe the message flowing through the message flow and perform checks after each node traversal. 
- **Logging and monitoring framework**
 - Monitoring - enables logs and business transaction monitoring on message flows (We would like you to use this Out of the box feature of IIB to gain advantages of business transaction monitoring done using MQ and Database schema). Although the set up is fairely complex, we did all the hardwork to figure out exact steps for you and note that this needs to be done only once.
 - Exception Handling (TBD)
 - Logging (TBD) - Use log4j in order to debug, develop and monitor details the business logic. Feed to splunk or queue as and when needed by tweaking log4j properties. 
- **Transformation Framework**
 - The transformation framework is designed to make transformation, independent of an ESB context, they can be independently tested - done entirely in Java so that transformation from any format to any other format such as JSON to XML, Plain Text to JSON, XML to Pojo etc. are abosolutely easy, developer controlled within Java code and fully test-automated (Sample Transformation Business logic Code with and without cache provided)
 - User defined variables - an example of how we would like you to consume the user defined variables in this fraemwork
 - Pattern for calling another REST API such as ZOSCOnnect - Performs transformation before calling a a REST API, Execute a REST API, and then perform transformatino on the result 
- **Build Framework**
 - Github repository source code management and bring all related projects under one umbrella
 - The Projects are places in flat structure to comply with IIB norms as well as simplicity of the code management. 
 - We chose to create a self contained bar file that can run in its own classloader. Gives advantages of Java Isolation of IIB 10
 - The Java projects such as Business Logic and FLow Test frameworks are maven projects that integrate well with DevOps and produce jar files that can be tested in an automated manner before deploy.

### Before using the IIB Application Framework
You need to create a one time setup of your toolkit so that all the IIB dependecies are resolved locally for your local development. If you are using a preconfigured VM, this is already done for you.

**Register IIB plugins into maven repository**
```
yum install maven

cd /root/IIB/iib-10.0.0.4/tools/plugins/com.ibm.etools.mft.jcn_10.0.400.v20160310-1307

mvn install:install-file -Dfile=jplugin2.jar -DgroupId=com.ibm.iib -DartifactId=jplugin2 -Dversion=1.0.0 -Dpackaging=jar

mvn install:install-file -Dfile=javacompute.jar -DgroupId=com.ibm.iib -DartifactId=javacompute -Dversion=1.0.0 -Dpackaging=jar

cd /root/IIB/iib-10.0.0.4/common/classes

mvn install:install-file -Dfile=IntegrationAPI.jar -DgroupId=com.ibm.iib -DartifactId=IntegrationAPI -Dversion=1.0.0 -Dpackaging=jar
```
**Build the ProcessingCOre Common framework**

1. In your git repositories view select -> **clone a git repository** -> Enter https://github.com/sanketsw/ProcessingCore.git and credentials -> After import is finished, right click on the repository and **import projects** -> import as exisitng projects -> select all projects.

2. run maven clean package install on **ProcessingCore** project (right click pom.xml -> run as maven build-> enter goal as `clean package install`) so that your IIB Application's maven build can find it in the local maven repository


### How to import the project in IIB

1. In IIB toolkit, goto Window -> open perspective -> Git. If you dont see Git Repositories view, then goto Window -> Show view -> Git Repositories

2. In your git repositories view select -> **clone a git repository** -> Enter https://github.com/sanketsw/JsonJsonApplication.git and credentials -> After import is finished, right click on the repository and **import projects** -> import as exisitng projects -> select all projects.

3. Right click and refresh the projects. If dependencies in Java project are not resolved, goto Project -> clean. Deploy the IIB application to default node.
>>>>>>> branch 'master' of https://github.com/sanketsw/JsonJsonApplication.git
 
### How to execute the API

**POST Mehtod**

POST  **http://localhost:7800/apiJson**

Request Header: **Content-Type: application/json**

Request Body: JSON Input of the API such as  {  "left": 5,  "right": 6 }

Note that User defined variables are by default set set to REST_URL=http://api-springboot.mybluemix.net/operate/addJSON, HTTP_METHOD=POST. If you like to change them, refer to https://github.com/sanketsw/HttpRequestRestAPI.
Sample screenshot below: (Response will differ)

![21603bfe7d199f47-1](https://cloud.githubusercontent.com/assets/14492591/14194959/a000b03e-f7ff-11e5-8758-6ab483dc3f1b.jpg)



### What is difference in IIB REST API and an IIB Application
Instead of IIB REST API, This one is IIB Application. Created using IBM Integration Bus 10.0.0.4. The REST API needs swagger specification. Application can create a REST API without swagger definition and with more flexibility of input output formats. Swagger specifications can be created independently.

The functionality of this application is same as https://github.com/sanketsw/HttpRequestRestAPI.git (more details in its readme file)

the comparable REST API implementation in IIB is **JsonJsonRESTAPI**:  https://github.com/sanketsw/JsonJsonRestAPI. 
