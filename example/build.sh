###############################################################################
# Copyright 2017 Samsung Electronics All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
###############################################################################

#!/bin/sh
build_home=$(pwd)
cd ../util/mapper/mavenProjects
mapper_home=$(pwd)
cd $edge_opcua_home

#start install mapper
cd $mapper_home
if [ ! -d "target" ]
then
    ./build.sh
else
    echo "mapper is already built"
fi

cd $build_home
cd ../edge-opcua
client_home="opcua-client"
server_home="opcua-server"
build_cmd="mvn clean compile assembly:single"

$build_cmd

mvn install:install-file -Dfile=./target/opcua-adapter-0.0.1-SNAPSHOT-jar-with-dependencies.jar -DgroupId=com.edge.protocol -DartifactId=opcua-adapter -Dversion=0.0.1-SNAPSHOT-jar-with-dependencies -Dpackaging=jar -DgeneratePom=true

cd $build_home
cd $client_home
$build_cmd
cp ./target/opcua-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar ../target
cd ../$server_home
$build_cmd
cp ./target/opcua-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar ../target
