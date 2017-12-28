OPC-UA Protocol Stack
================================

This provides opcua protocol stack library. 
Services such as opcua device service and opcua expert service needs pre-requirements whcih build this protocol stack.

## Prerequisites ##

- JDK
  - Version : 1.8
  - [How to install](https://docs.oracle.com/javase/8/docs/technotes/guides/install/linux_jdk.html)
  - Eclipse Setting
     - Select Project -> Preference - Java Build Path - JRE System Library -Edit -> 
            Installed JREs - Add - Standard VM - Next -input Path installed JDK 1.8 -> finish 
- Maven
  - Version : 3.5.2
  - [Where to download](https://maven.apache.org/download.cgi)
  - [How to install](https://maven.apache.org/install.html)
  - [Setting up proxy for maven](https://maven.apache.org/guides/mini/guide-proxies.html)

## How to build  ##

#### 1. cmd line ####

```shell
$ ./build.sh
```
  ![build_stack](../documents/readme_images/build_stack.png)

#### 2. Eclipse ####

## Test ##

#### Test OPC-UA sample application [here](./example/README.md)
