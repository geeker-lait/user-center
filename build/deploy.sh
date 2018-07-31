#!/usr/bin/env bash
echo '=====start build====='
sh build.sh
echo '=====end build====='

echo '=====start push the docker image====='
sh push.sh
echo '=====end push the docker image====='

echo '=====start run the docker image====='
sh run.sh
echo '=====end run the docker image====='
