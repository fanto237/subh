FROM jboss/wildfly:9.0.1.Final
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
COPY target/subh.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]