FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM joengenduvel/jre17:latest
WORKDIR /app
COPY --from=builder /app/build/libs/SampleSpringBoot-Kotlin-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080