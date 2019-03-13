# -*- coding: utf-8 -*-
# Create By evan

from fabric.api import *

env.hosts = ['root@10.211.55.4']

env.user = 'root'
env.password = 'dev'


def build():
    local('python setup.py sdist')

def publish():

    agent_dir = '/opt/deploy-agent/'
    data_dir = '/opt/data/deployd/'
    run('mkdir -p ' + agent_dir)
    run('mkdir -p ' + data_dir)

    file_name = 'deploy-agent-1.2.15.tar.gz'
    dist_file = 'dist/'+file_name
    put(dist_file, agent_dir)
    with cd(agent_dir):

        run("pip install "+file_name+"  -i http://pypi.douban.com/simple --trusted-host pypi.douban.com")
        run('mkdir -p ' + data_dir + 'builds')
        run('mkdir -p ' + data_dir + 'logs')
        run('mkdir -p ' + data_dir + 'targets')

        put('deployd/conf/agent.conf', agent_dir)

        ###########
        run('echo "groups=java" >' + data_dir + 'host_info')
        run('echo "id=t105" >>' + data_dir + 'host_info')
        run('echo "host=t105" >>' + data_dir + 'host_info')
        run('echo "ip=192.168.0.105" >>' + data_dir + 'host_info')
