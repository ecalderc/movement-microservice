FROM openjdk:17
EXPOSE 8088
ADD target/movement-microservice-0.0.1-SNAPSHOT.jar movement-microservice.jar
ENTRYPOINT ["java","-jar","/movement-microservice.jar"]