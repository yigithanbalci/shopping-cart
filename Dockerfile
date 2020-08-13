### RUN STAGE
FROM openjdk:8-jre-slim
WORKDIR /app
COPY target/*.jar app.jar
COPY src/main/resources/application.properties application.properties
EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["java", "-Duser.timezone=+03:00", "-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n", "-Xms2g", "-Xmx8g", "-jar", "app.jar", "--spring.config.location=file:./application.properties"]
