FROM maven:3.8.5-openjdk-17-slim AS build
VOLUME /tmp
COPY .. ./
RUN mvn clean package -DskipTests

FROM openjdk:17
VOLUME /tmp
EXPOSE 8088
COPY --from=build /target/movement-microservice-0.0.1-SNAPSHOT.jar ./movement-microservice.jar
ENTRYPOINT ["java","-jar","/movement-microservice.jar"]