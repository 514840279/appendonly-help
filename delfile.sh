#!/bin/bash
log=appendonly.aof
line=26
A=$(sed -n '$=' $log)
sed -i $(($A-$line+1)),${A}d $log

