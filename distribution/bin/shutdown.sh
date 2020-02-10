#!/usr/bin/env bash

pid=`ps aux | grep "pnd-web.jar" | grep -v grep|awk '{print $2}'`
if [ -z $pid ]; then
    echo "no pnd server running..."
    exit -1
fi

echo "kill pnd server..."
kill $pid
echo "success kill server at pid:${pid}"