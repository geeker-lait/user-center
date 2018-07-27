#!/usr/bin/env bash
docker login 192.168.1.162:5000 -u admin --password admin123
docker push 192.168.1.162:5000/lmt-mbsp-user-server