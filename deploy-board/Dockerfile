FROM python:2.7
ENV PYTHONUNBUFFERED 1 
ENV PROJECT_DIR=/opt/deploy-board

#ADD source.list /etc/apt/sources.list
#ADD uwsgi.ini /uwsgi.ini
COPY requirements.txt /requirements.txt
COPY pip.conf /root/.pip/pip.conf


RUN mkdir $PROJECT_DIR
WORKDIR $PROJECT_DIR

#RUN apt-get update
RUN pip install -r /requirements.txt
RUN pip install uwsgi

ADD . $PROJECT_DIR
#Install PIP packages

EXPOSE 8888


