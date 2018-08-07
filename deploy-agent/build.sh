#!/bin/bash

docker run -it -v $PWD:/opt/deploy-agent -w /opt/deploy-agent python:2.7 python setup.py sdist
