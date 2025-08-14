# Dockerfile
FROM eclipse-temurin:21

ARG JAR_FILE=target/CodeArenaProfile-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} CodeArenaProfile-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/CodeArenaProfile-0.0.1-SNAPSHOT.jar"]