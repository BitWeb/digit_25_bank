FROM eclipse-temurin:21-jre

WORKDIR /app
COPY app.jar /app

CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
