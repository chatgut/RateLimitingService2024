FROM maven:3.9.6-eclipse-temurin-22-alpine AS build
WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn clean package

FROM eclipse-temurin:22-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
