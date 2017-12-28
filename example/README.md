OPC-UA Sample Application
================================

It provides example codes of both client and server. It will be helpful usage of opcua stack library.

## How to build  ##

#### Command ####

```shell
$ ./build.sh
```
   ![build_exam](../documents/readme_images/build_exam.png)

##### Executable binary #####
  - Server
    - Executable : target/opcua-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar
  - Client
    - Executable : target/opcua-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar

![example_jars](../documents/readme_images/example_jars.png)

#### Eclipse ####

##### Prerequisites #####
- opcua stack library
  - [build opcua stack library](../README.md)
 
1. Import opcua stack
   File - Import -Existing Maven Project - next
     -> select Root Directory in your source directory : *protocol-opcua-java/edge-opcua*
     -> finish

2. Import sample application
   File - Import -Existing Maven Project - next
     -> select Root Directory in your source directory : *protocol-opcua-java/example*
     -> finish

3. Run as 'java application' in both opcua-client and opcua-server projects

- *Reference* : If you have some error related dependency in pom.xml, please update maven project following below
               - 'Select Project' -> 'Click Right button' 
               -> 'Maven' -> 'Update Maven Project' -> Check 'Force Update of Snapshots/Releases' -> OK

## How to run ##

#### Test ####
#### 1. Executable binary ####
- *server*
   ![server_1](../documents/readme_images/server_1.png)
```shell
$ java -jar target/opcua-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
  ##### menu of server #####

   *start* : start opcua server / create Node  -> first of all, you should input this command for testing

   *getnode* : get node information

   *getnode2* : get node information with browse name

   *quit* : terminate server

   *help* : show menu

- *client*
   ![client_1](../documents/readme_images/client_1.png)
```shell
$ java -jar target/opcua-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
  ##### menu of client #####

   *start* : start opcua client / connect with opcua server / intialize Service Provider

   *quit* : terminate client

   *provider* : get the provider list

   *browse* : browse all nodes with root node

   *browse_m* : multiple browse with several target nodes

   *read_s* : read attribute for server node

   *read_t* : read attribute for target node

   *read_gp* : multiple read attribute for target nodes

   *write* : write attribute to target node

   *write_gp* : multiple write attributes to nodes

   *sub* : subscription 

   *sub_modify* : modfiy subscription

   *sub_delete* : delete subscription

   *endpoint* : get endpoints from the server

   *auto* : run test automatically

   *help* : show menu

#### 2. Start server ####
   input `start` and then, input `Your IP Address` in **OPC-UA Server**.
   ![server_2](../documents/readme_images/server_2.PNG)
   
   Then start *OPC-UA Server*. And create nodes.
   ![server_3](../documents/readme_images/server_3.PNG)
#### 3. Start Client ####
   input `start` and then, input `opc.tcp://[OPC-UA Server's IP Addreess]/edge-opc-server` in **OPC-UA Client**.
   ![client_2](../documents/readme_images/client_2.PNG)
   
   Then, *OPC-UA Client* connect with *OPC-UA Server*. <br>
   And create service provider which indicate target node.
   ![client_3](../documents/readme_images/client_3.PNG)
   
   And, Show *the alias of the provider*
   ![client_4](../documents/readme_images/client_4.PNG)
#### 4. Read Command ####
   input `read_t` and then, input the alias of the provider which indicate target node. (refer getting *the alias of the Provider step*).<br>
   Then read attribute from server node.
   ![client_5](../documents/readme_images/client_5.PNG)
#### 5. Write Command ####
   input `write` and then, input the alias of the provider which indicate target node.<br>
   Then write to the target node of the opcua server. 
   ![client_6](../documents/readme_images/client_6.PNG)
#### 6. Monitoring  ####
   input `start CNC` in *OPC-UA Server*.<br>
   Then update value of the 'cnc100' node
   ![server_8](../documents/readme_images/server_8.PNG)
   
   input `sub` command in *OPC-UA Client*. And then, input the alias of the provider which indicate target node.<br>
   Then request subscription to target node. you can receive changed value from the target node.
   ![client_7](../documents/readme_images/client_7.PNG)
   ![client_8](../documents/readme_images/client_8.PNG)


