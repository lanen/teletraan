FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/deploy-service
RUN mkdir -p /opt/deploy-service/bin
RUN mkdir -p /opt/deploy-service/lib

COPY teletraanservice/bin/entrypoint.sh /opt/deploy-service/
COPY teletraanservice/bin/server.yaml /opt/deploy-service/
COPY teletraanservice/target/lib /opt/deploy-service/lib
COPY common/target/common-0.1-SNAPSHOT.jar /opt/deploy-service/lib/
COPY teletraanservice/target/teletraan-service-0.1-SNAPSHOT.jar /opt/deploy-service/lib/

EXPOSE 8080

# /opt/deploy-service
# bin/run
# bin/server.yaml
# lib

WORKDIR /opt/deploy-service
ENTRYPOINT [ "/bin/sh", "/opt/deploy-service/entrypoint.sh", "-c", "/opt/deploy-service/server.yaml", "run" ]

