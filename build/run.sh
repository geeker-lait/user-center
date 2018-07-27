#!/usr/bin/env bash

network=pinpoint_pinpoint
vol_from=pinpoint-agent
#docker_registry=docker.lmt.com
docker_registry=192.168.1.162:5000

service_name=lmt-mbsp-user-server
service_port=8080
docker stop ${service_name}
docker rm ${service_name}
docker run -d --name=${service_name} -p ${service_port}:${service_port} --restart=always --network=${network} --volumes-from=${vol_from} ${docker_registry}/${service_name}
