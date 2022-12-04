FROM python:3 AS sql
COPY ./data/index.html /home/data/index.html
COPY ./generate_sql.py /scripts/script.py
RUN python3 /scripts/script.py /home/data/index.html -o /home/data/data.sql

FROM gradle:7.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
COPY --from=sql /home/data/data.sql /home/gradle/src/main/resource/data.sql
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:17 as run
EXPOSE 8080
ARG JAR_FILE=/home/gradle/src/build/libs/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
