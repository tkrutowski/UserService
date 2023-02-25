FROM adoptopenjdk/openjdk11:jdk-11.0.2.7-alpine-slim
ADD java.security /opt/java/openjdk/conf/security
ADD target/user-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8089
CMD java -jar user-service-0.0.1-SNAPSHOT.jar
