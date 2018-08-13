FROM docker.lmt.com/openjre8-apline

MAINTAINER lex meng "lex@lmt21.com"

ENV PINPOINT_VERSION 1.7.3
ENV APP_NAME @project.build.finalName@.@project.packaging@

ENV JAVA_OPTS="-javaagent:/pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar -Dpinpoint.agentId=${APP_NAME} -Dpinpoint.applicationName=${APP_NAME}"

VOLUME /tmp
ADD ${APP_NAME} app.jar

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar