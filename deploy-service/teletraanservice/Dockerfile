From java:8
ADD source.list /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y php5 php5-curl

ENV PROJECT_DIR=/opt/deploy-service

RUN mkdir $PROJECT_DIR
WORKDIR $PROJECT_DIR
