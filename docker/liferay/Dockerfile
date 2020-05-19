FROM liferay/dxp:7.1.10.2

MAINTAINER Allan Revolorio <allan@firelay.com>

USER root
COPY resources/wait-for-it.sh /etc/liferay/wait-for-it.sh
RUN chown liferay:liferay /etc/liferay/wait-for-it.sh
RUN chmod a+x /etc/liferay/wait-for-it.sh

USER liferay

ENV VIRTUAL_PORT 8080
ENV LOGGING_LEVEL DEBUG
ENV CATALINA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=12345 -Djava.rmi.server.hostname=$HOSTIP

ENTRYPOINT /etc/liferay/wait-for-it.sh database:3306 --strict -- /usr/local/bin/liferay_entrypoint.sh