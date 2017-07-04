FROM java:openjdk-8-jdk
ADD target/wildfly-swarm-keycloak-server-demo-swarm.jar /opt/login-service-swarm.jar
ADD keycloak.h2.db /opt/keycloak.h2.db
#ADD keycloak.lock.db /opt/keycloak.lock.db
ADD keycloak.trace.db /opt/keycloak.trace.db

EXPOSE 8180
ENTRYPOINT ["java", "-jar", "/opt/login-service-swarm.jar", "-Dwildfly.swarm.keycloak.server.db=/opt/keycloak"]
ENTRYPOINT ["java", "-jar", "-Dswarm.port.offset=100", "/opt/login-service-swarm.jar"]
