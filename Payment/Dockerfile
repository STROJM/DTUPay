#@author Søren Andersen s182881
# @author: Mikkel Denker (s184193)
FROM adoptopenjdk:11-jre-hotspot
COPY target/lib /usr/src/lib
COPY target/Payment-1.0.0.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx64m -jar Payment-1.0.0.jar
