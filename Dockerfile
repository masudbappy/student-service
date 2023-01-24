FROM openjdk:11
EXPOSE 8081
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} student-service.jar
ENTRYPOINT ["java", "-jar", "/stuedent-service.jar"]