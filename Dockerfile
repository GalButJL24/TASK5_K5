FROM openjdk:17-jdk-alpine
COPY target/TASK5_K5-1.0-SNAPSHOT.jar  applTask5.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "applTask5.jar"]