FROM adoptopenjdk/openjdk8:ubi
RUN mkdir /opt/data
COPY target/integration-analyst-test*.jar application.jar
ENTRYPOINT [ "java" , "-jar", "application.jar", "--path=/opt/data"]