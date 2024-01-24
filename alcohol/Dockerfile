FROM openjdk:17
ARG JAR_FILE=build/libs/alcohol.jar
COPY ${JAR_FILE} ./alcohol.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./alcohol.jar"]