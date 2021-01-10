FROM maven:3-openjdk-11-slim as builder
WORKDIR /build
COPY . .
RUN mvn package

#####
FROM adoptopenjdk:11-jre-openj9

ENV JAVA_OPTS="-Xms96m -Xmx128m -XX:MetaspaceSize=96m -XX:MaxMetaspaceSize=128m"

COPY --from=builder /build/target/covid-fluidapi-spring.jar /app/
RUN groupadd -r fluidapiuser \
        && useradd --no-log-init -g fluidapiuser fluidapiuser \
        && chown -R fluidapiuser:fluidapiuser  /app

USER fluidapiuser
WORKDIR /app
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-server", "-XX:+UseContainerSupport", "-jar", "covid-fluidapi-spring.jar"]
