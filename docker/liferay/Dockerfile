FROM liferay/dxp:7.1.10-dxp-28

MAINTAINER Allan Revolorio <allan@firelay.com>

USER root

COPY resources/wait-for-it.sh /etc/liferay/wait-for-it.sh
RUN chown liferay:liferay /etc/liferay/wait-for-it.sh
RUN chmod a+x /etc/liferay/wait-for-it.sh

RUN chown -R liferay:liferay /opt/liferay/data/document_library

COPY ["resources/keystore.jks", "/opt/liferay/data/keystore.jks"]
#COPY ["resources/Liferay Connector to SAML 2.0.lpkg", "/opt/liferay/osgi/marketplace/Liferay Connector to SAML 2.0.lpkg"]
#COPY ["resources/Liferay Plugin for OAuth 2.0.lpkg", "/opt/liferay/osgi/marketplace/Liferay Plugin for OAuth 2.0.lpkg"]
COPY ["resources/activation-key.xml", "/opt/liferay/osgi/modules/activation-key.xml"]


USER liferay

ENV VIRTUAL_PORT 8080
ENV LOGGING_LEVEL DEBUG
ENV HOSTNAME liferay
ENV CATALINA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=12345 -Djava.rmi.server.hostname=$HOSTNAME
# Include Yourkit profiling
#ENV CATALINA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=12345 -Djava.rmi.server.hostname=$HOSTNAME -agentpath:/usr/local/YourKit-JavaProfiler-2022.9/bin/linux-x86-64/libyjpagent.so=port=10001,listen=all
ENTRYPOINT /bin/bash /etc/liferay/wait-for-it.sh database:3306 --strict -- /usr/local/bin/liferay_entrypoint.sh