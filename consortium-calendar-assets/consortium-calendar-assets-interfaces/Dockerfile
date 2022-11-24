FROM azul/zulu-openjdk:17-latest
LABEL MAINTAINER="Heli"

VOLUME /tmp
ARG JAR_FILE
COPY --chown=application:application ${JAR_FILE} application.jar

ENV TZ=Asia/Seoul
EXPOSE 8080

ENTRYPOINT exec java $JAVA_POTS -jar ./application.jar
CMD []
