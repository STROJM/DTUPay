# @author: Mikkel Denker (s184193)
FROM adoptopenjdk:11-jre-hotspot

COPY target/quarkus-app/lib /usr/src/lib
COPY target/quarkus-app/app /usr/src/app
COPY target/quarkus-app/quarkus /usr/src/quarkus
COPY target/quarkus-app/quarkus-run.jar /usr/src/

WORKDIR /usr/src/
CMD java -Xmx64m -jar quarkus-run.jar
