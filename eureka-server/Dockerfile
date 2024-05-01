FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY target/eureka-server-1.0-SNAPSHOT.jar /opt/app/eureka-server-1.0-SNAPSHOT.jar
CMD ["java", "-jar", "/opt/app/eureka-server-1.0-SNAPSHOT.jar"]
EXPOSE 8761