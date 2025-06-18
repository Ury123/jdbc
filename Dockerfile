FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/jdbc.jar app.jar

ENV JAVA_OPTS="-Xms128m -Xmx256m"

ENTRYPOINT ["java", "-jar", "app.jar"]