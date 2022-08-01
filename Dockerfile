FROM openjdk:18
ARG JAR_FILE=target/mockmockmock-1.0-SNAPSHOT-shaded.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]