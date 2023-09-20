FROM docker.elastic.co/elasticsearch/elasticsearch:7.16.1

MAINTAINER Allan Revolorio <allan@firelay.com>

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-icu
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-kuromoji
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-smartcn
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-stempel