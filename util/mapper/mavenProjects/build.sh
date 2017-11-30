#!/bin/sh
PROJECT_ROOT=$(pwd)
echo $PROJECT_ROOT

#start package mapper
cd $PROJECT_ROOT
mvn clean install -U
echo "done"

