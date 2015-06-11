#!/bin/bash

currentDir=`pwd`
file="version.log"

currentDir=`pwd`
file="version.log"
str="abc|def|ghi|jkl"
IFS="|"
arrStr=($str)
#${#arrAllIndicatorsApp[@]}
strNum=${#arrStr[@]}
myFile=$currentDir"/"$file
echo "========================="$strNum
appVer=`awk -F, '{
val="";
awkStr="'"${arrStr[*]}"'";
tlen=split(awkStr,awkStrKey,"|");
for(i=1;i<=tlen;i++){
    printf("\"%s\"", awkStrKey[i])
    if ( i == tlen )
        printf (":%s",$i);
    else
        printf (":%s,",$i);
    fi
}

[hadoop@hadoop ]$cat version.log 
version-00000000,1110,1113,1116
version-00000001,1111,1114,1117
version-00000002,1112,1115,1118
version-00000003,1113,1116,1119