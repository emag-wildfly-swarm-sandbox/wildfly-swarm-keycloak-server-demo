FROM java:openjdk-8-jdk
ADD target/wildfly-swarm-keycloak-server-demo-swarm.jar /opt/login-service-swarm.jar
ADD keycloak.h2.db /opt/keycloak.h2.db
ADD keycloak.trace.db /opt/keycloak.trace.db

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/login-service-swarm.jar", "-Dswarm.port.offset=100", "-Dwildfly.swarm.keycloak.server.db=/opt/keycloak"]
