FROM jboss/wildfly:latest

# Copy the modified WAR file to the WildFly deployments directory
COPY target/AplicacioWeb-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

# Expose the port 8082 (if you want to use this port instead of the default 8080)
EXPOSE 8082
