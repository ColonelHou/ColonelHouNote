#!/bin/bash
IFSBAK=$IFS
IFS='='
while read k v
do
  echo $k
  echo $v
done < db.properties
IFS=$IFSBAK
===================
echo $dbName
