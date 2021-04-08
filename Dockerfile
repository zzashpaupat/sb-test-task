FROM adoptopenjdk/openjdk11:alpine
RUN mkdir -p /app/

ARG JAR_FILE=build/libs/covid-stats-app-*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]