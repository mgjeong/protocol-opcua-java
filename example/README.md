# How to test OPC-UA Sample Application

## Reference

- Run `./build.sh.`
  You can get OPC-UA Server/Client jar file in taret folder.
<br></br>
## Build
### How to build sample application in command line

1. Environment : Ubuntu 16.04 (64bit)

2. In *protocol-opcua-java/example*, run `./build.sh` in command line

   ![build_exam](../documents/readme_images/build_exam.png)

3. You can find opcua-client, opcua-server sample application
   
   `java -jar target/opcua-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
   
   `java -jar target/opcua-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar` follwing below

   ![example_jars](../documents/readme_images/example_jars.png)

### How to build sample application with Eclipse

1. Environment : Eclipse

2. Import opcua stack
   File - Import -Existing Maven Project - next
     -> select Root Directory in your source directory : *protocol-opcua-java/edge-opcua*
     -> finish

3. Import sample application
   File - Import -Existing Maven Project - next
     -> select Root Directory in your source directory : *protocol-opcua-java/example*
     -> finish

4. You can build source codes of the opcua stack and run as 'java application' in both opcua-client and opcua-server.

5. Reference : If you have some error related dependency in pom.xml. please update maven project following below.
               'Select Project' -> 'Click Right button' 
               -> 'Maven' -> 'Update Maven Project' -> Check 'Force Update of Snapshots/Releases' -> OK

   ![build_4_1](../documents/readme_images/build_4_1.png)
<br></br>
## Test

### 1. Execute *OPC-UA Server* <br>
   command : `java -jar target/opcua-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
   
   ![server_1](../documents/readme_images/server_1.png)

   *start* : start opcua server / create Node  -> first of all, you should input this command for testing

   *getnode* : get node information

   *getnode2* : get node information with browse name

   *quit* : terminate server

   *help* : show menu
<br>
### 2. Execute *OPC-UA Client* <br>
   command : `java -jar target/opcua-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar`

   ![client_1](../documents/readme_images/client_1.png)

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
<br>
### 3. Start Server <br>
   input `start` and then, input `Your IP Address` in **OPC-UA Server**.
   ![server_2](../documents/readme_images/server_2.PNG)
   
   Then start *OPC-UA Server*. And create nodes.
   ![server_3](../documents/readme_images/server_3.PNG)
<br>
### 4. Start Client <br>
   input `start` and then, input `opc.tcp://[OPC-UA Server's IP Addreess]/edge-opc-server` in **OPC-UA Client**.
   ![client_2](../documents/readme_images/client_2.PNG)
   
   Then, *OPC-UA Client* connect with *OPC-UA Server*. <br>
   And create service provider which indicate target node.
   ![client_3](../documents/readme_images/client_3.PNG)
   
   And, Show *the alias of the provider*
   ![client_4](../documents/readme_images/client_4.PNG)
<br>
### 5. Read Command<br>
   input `read_t` and then, input the alias of the provider which indicate target node. (refer getting *the alias of the Provider step*).<br>
   Then read attribute from server node.
   ![client_5](../documents/readme_images/client_5.PNG)
<br>     
### 6. Write Command<br>
   input `write` and then, input the alias of the provider which indicate target node.<br>
   Then write to the target node of the opcua server. 
   ![client_6](../documents/readme_images/client_6.PNG)
<br>     
### 7. Monitoring <br>
   input `start CNC` in *OPC-UA Server*.<br>
   Then update value of the 'cnc100' node
   ![server_8](../documents/readme_images/server_8.PNG)
   
   input `sub` command in *OPC-UA Client*. And then, input the alias of the provider which indicate target node.<br>
   Then request subscription to target node. you can receive changed value from the target node.
   ![client_7](../documents/readme_images/client_7.PNG)
   ![client_8](../documents/readme_images/client_8.PNG)



