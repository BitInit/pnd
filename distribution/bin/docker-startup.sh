#!/usr/bin/env sh

PND_HOME=`cd $(dirname $0)/..; pwd`

JAVA_OPTS="${JAVA_OPTS} -Dpnd.homeDir=${PND_HOME}"

#############################################################
# Mysql configuration
#############################################################
if [[ $USE_MYSQL == true ]]; then
    JAVA_OPTS="${JAVA_OPTS} -Dpnd.useMysql=true"
    JAVA_OPTS="${JAVA_OPTS} -Dpnd.mysql.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DB_NAME}"
    JAVA_OPTS="${JAVA_OPTS} -Dpnd.mysql.username=${MYSQL_USERNAME}"
    JAVA_OPTS="${JAVA_OPTS} -Dpnd.mysql.password=${MYSQL_PASSWORD}"
fi

JAVA_OPTS="${JAVA_OPTS} -jar "${PND_HOME}/lib/pnd-web.jar""
JAVA_OPTS="${JAVA_OPTS} --spring.config.location=${PND_HOME}/conf/application.properties"

if [ ! -d "${PND_HOME}/data/logs" ]; then
    mkdir -p ${PND_HOME}/data/logs
fi

java ${JAVA_OPTS}