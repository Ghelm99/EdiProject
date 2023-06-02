#
# Build stage
#
FROM maven:3.8.1-adoptopenjdk-11 AS build
COPY EdiSecuredBackend/src /home/app/src
COPY EdiSecuredBackend/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/EdiSecuredBackend-0.0.1-SNAPSHOT.jar /usr/local/lib/edi.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/edi.jar"]