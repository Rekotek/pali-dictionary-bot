FROM bellsoft/liberica-openjdk-alpine-musl:17.0.1-12

WORKDIR /app
COPY *.jar app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.jmx.enabled=false", "-Dsecurerandom.source=file:/dev/./urandom", "-Dspring.profiles.active=production", "-jar", "app.jar"]