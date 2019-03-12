FROM python:2.7.16-alpine3.9
ENV PYTHONUNBUFFERED 1 
ENV PROJECT_DIR=/opt/deploy-board

#ADD uwsgi.ini /uwsgi.ini
COPY requirements.txt /requirements.txt
COPY pip.conf /root/.pip/pip.conf

RUN mkdir $PROJECT_DIR
WORKDIR $PROJECT_DIR

#RUN apt-get update
RUN pip install -r /requirements.txt && rm -rf ~/.cache/pip
#RUN pip install uwsgi

ADD . $PROJECT_DIR
#Install PIP packages

EXPOSE 8888

#ENTRYPOINT [ "/usr/local/bin/uwsgi", "--ini", "/opt/deploy-board/uwsgi.ini"]
ENTRYPOINT [ "python", "/opt/deploy-board/manage.py", "runserver", "0.0.0.0:8888" ]
