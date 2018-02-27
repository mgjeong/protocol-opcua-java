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
mvn clean install assembly:single -U -Dmaven.test.skip=true

echo "End of edge opcua build"
