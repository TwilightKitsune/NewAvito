#FROM openjdk:11
#LABEL authors="Katerina"
#VOLUME /tmp
#EXPOSE 8080
#ARG JAR_FILE=out/artifacts/NewAvito_jar/NewAvito.jar
#ADD ${JAR_FILE} NewAvito.jar
#
#COPY src/main/resources/ .
#
#ENTRYPOINT ["java","-jar","/NewAvito.jar"]
#COPY . /myapp
#WORKDIR /myapp
#RUN javac NewAvitoApplication.java
#CMD ["java", "myapp/src/main/java/com/kitsune/NewAvito/NewAvitoApplication"]


#FROM maven:3.8.4-jdk-11 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package


FROM eclipse-temurin:11-jdk
WORKDIR /app
COPY target/NewAvito-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
