# Use official Java image
FROM eclipse-temurin:17-jdk

# Copy jar
COPY target/*.jar app.jar

# Run app
ENTRYPOINT ["java","-jar","/app.jar"]