FROM openjdk:11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build/libs/*.war application.war
EXPOSE 8080
CMD ["java", "-jar", "application.war"]