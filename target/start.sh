#!/bin/zsh

sed -i "" "s#^tags=.*#tags=$1#g"  config.properties
sed -i "" "s#^categories=.*#categories=$2#g"  config.properties
sed -i "" "s#^title=.*#tags=$3#g"  config.properties

java -jar csdn2hexo-1.0-SNAPSHOT.jar

