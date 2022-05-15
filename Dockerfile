FROM openjdk:11

COPY "./target/belepteto.jar" "/application/belepteto.jar"

CMD ["java", "-jar", "/application/belepteto.jar"]
