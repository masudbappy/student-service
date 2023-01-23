FROM openjdk:11
EXPOSE 8081
ADD build/libs/student-service.jar student-service.jar
ENTRYPOINT ["java", "-jar", "stuedent-service.jar"]