FROM openjdk:8-jre-alpine

WORKDIR /pnd
COPY . /pnd

RUN echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8989
VOLUME /pnd/data
ENTRYPOINT ["bin/docker-startup.sh"]