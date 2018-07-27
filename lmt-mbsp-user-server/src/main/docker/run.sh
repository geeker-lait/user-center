#!/usr/bin/env bash
docker run -d --name=@project.build.finalName@ \
    --network=pinpoint_pinpoint \
    --volumes-from=pinpoint-agent \
    docker.lmt.com/@project.build.finalName@