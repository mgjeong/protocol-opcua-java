#!/bin/sh
echo "Start edge opcua build"
edge_opcua_home=$(pwd)
cd ./util/mapper/mavenProjects
mapper_home=$(pwd)
cd $edge_opcua_home


#start install mapper
cd $mapper_home
./build.sh

#start install edge_opcua
cd $edge_opcua_home
cd ./edge-opcua
mvn clean install -U -Dmaven.test.skip=true
rm -rf target/

echo "End of edge opcua build"
