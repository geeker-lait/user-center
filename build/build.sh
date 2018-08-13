#!/usr/bin/env bash
echo '=====update coed from gitlab====='
git pull
echo '=====mvn install & docker:build====='
mvn -f ~/build/user-center/pom.xml clean install -U -DskipTests
mvn -f ~/build/user-center/lmt-mbsp-user-front/pom.xml docker:build
mvn -f ~/build/user-center/lmt-mbsp-user-backend/pom.xml docker:build