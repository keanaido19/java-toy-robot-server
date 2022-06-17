FROM ubuntu:latest
MAINTAINER keaton naidoo <keanaido021@student.wethinkcode.co.za>

ENV SDKMAN_DIR /root/.sdkman
ENV JAVA_VERSION 11.0.15.9.1-amzn

RUN ["mkdir", "-p", "/apps/home"]
COPY target/SocketServer*.jar /apps/home/RobotWorldsServer.jar
RUN apt-get update && apt-get install -y zip curl
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "$SDKMAN_DIR/bin/sdkman-init.sh"

RUN set -x \
    && echo "sdkman_auto_answer=true" > $SDKMAN_DIR/etc/config \
    && echo "sdkman_auto_selfupdate=false" >> $SDKMAN_DIR/etc/config \
    && echo "sdkman_insecure_ssl=false" >> $SDKMAN_DIR/etc/config

WORKDIR $SDKMAN_DIR
RUN [[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh" && exec "$@"

RUN source /root/.bashrc
RUN source "$SDKMAN_DIR/bin/sdkman-init.sh" && sdk install java $JAVA_VERSION

ENV JAVA_HOME="$SDKMAN_DIR/candidates/java/current"
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /apps/home
EXPOSE 5050
CMD ["java", "-jar", "RobotWorldsServer.jar","-p","5050"]