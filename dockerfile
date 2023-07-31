FROM openjdk:17-oracle
WORKDIR /app
COPY app/build/libs/app-0.0.1-SNAPSHOT.jar spring-boot-web.jar
EXPOSE 8080
CMD ["java", "-jar", "spring-boot-web.jar"]