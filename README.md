# OPC-UA Protocol Stack

This provides opcua protocol stack library. Services such as opcua device service and opcua expert service needs pre-requirements whcih build this protocol stack.
And this protocol stack has some pre-requirements (JDK, maven) which build like below.
<br></br>

## OPC-UA library for Java

### JDK 1.8

#### Intall JDK v1.8

Install Guide : https://docs.oracle.com/javase/8/docs/technotes/guides/install/linux_jdk.html

Eclipse Setting

Select Project -> Preference - Java Build Path - JRE System Library -Edit -> Installed JREs - Add - Standard VM - Next - input Path installed JDK 1.8 -> finish 
<br></br>

#### maven

Install maven v3.5.2

Download : https://maven.apache.org/download.cgi

Install Guide : https://maven.apache.org/install.html

tar xzvf apache-maven-3.5.2-bin.tar.gz

Add the bin directory of the created directory apache-maven-3.5.2 to the PATH environment variable
<br></br>

#### How to build OPC-UA library [here](./java/edge-opcua/README.md)

#### Test OPC-UA sample application [here](./java/example/README.md)

