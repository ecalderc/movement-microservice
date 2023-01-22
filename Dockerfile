FROM openjdk:17
EXPOSE 8082
ADD target/customer-microservice-0.0.1-SNAPSHOT.jar customer-microservice.jar
ENTRYPOINT ["java","-jar","/customer-microservice.jar"]