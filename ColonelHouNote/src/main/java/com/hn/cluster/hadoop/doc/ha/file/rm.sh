#!/usr/bin/env bash
rm -r /usr/local/src/hadoop/data_conf/*
rm -r /usr/local/src/hadoop/logs/*



mkdir -p /usr/local/src/hadoop/data_conf/hadoop_pid/
mkdir -p /usr/local/src/hadoop/data_conf/tmp/
mkdir -p /usr/local/src/hadoop/data_conf/tmp/yarn/local/
mkdir -p /usr/local/src/hadoop/data_conf/yarn-leader-election/
