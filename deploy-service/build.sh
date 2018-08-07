#!/bin/bash
# docker pull maven
docker run -it -v /root/.m2:/root/.m2 -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven mvn clean package -DskipTests
