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
echo "Start edge opcua unit test"
edge_opcua_home=$(pwd)

#start install edge_opcua
cd $edge_opcua_home
cd ./edge-opcua
mvn test
#check unit test fail
if [ $? -ne 0 ]; then
        echo "Unittest is failed."; exit 1
fi

echo "End of edge opcua unit test"
