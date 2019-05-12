#!/usr/bin/env sh

PND_HOME=`cd $(dirname $0)/..; pwd`

JAVA_OPTS="${JAVA_OPTS} -Dpnd.home=${PND_HOME}"
JAVA_OPTS="${JAVA_OPTS} -jar "${PND_HOME}/lib/pnd-web.jar""
JAVA_OPTS="${JAVA_OPTS} --spring.config.location=${PND_HOME}/conf/application.properties"

if [ ! -d "${PND_HOME}/data/logs" ]; then
    mkdir -p ${PND_HOME}/data/logs
fi

echo "starting pnd..."
nohup java ${JAVA_OPTS} > ${PND_HOME}/data/logs/pnd-start.log 2>&1 < /dev/null