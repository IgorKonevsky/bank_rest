FROM gradle:8.6-jdk21 AS builder
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .

RUN ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
