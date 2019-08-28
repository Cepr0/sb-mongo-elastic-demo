FROM openjdk:8-jre-alpine
VOLUME /tmp
WORKDIR /service
COPY target/*.jar service.jar
COPY target/elastic/elastic-apm-agent.jar elastic-apm-agent.jar
ENTRYPOINT exec java -server \
-noverify \
-XX:TieredStopAtLevel=1 \
-XX:+UnlockExperimentalVMOptions \
-XX:+UseCGroupMemoryLimitForHeap \
-javaagent:elastic-apm-agent.jar \
-Dspring.jmx.enabled=false \
$JAVA_OPTS \
-jar service.jar
