FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/config-server-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]