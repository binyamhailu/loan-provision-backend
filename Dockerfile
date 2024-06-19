FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/loan-provisioning-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]