#!/bin/bash
while :
do
    echo "abc" >> log
    curl http://192.168.13.74/log/startLog
    sleep 3s
done