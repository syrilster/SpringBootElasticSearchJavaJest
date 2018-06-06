# To run this demo, we should follow this steps

Prerequisite-1: Install Java and set JAVA_HOME and PATH variables.

Prerequisite-2: Install Maven.

Prerequisite-3: Install Elasticsearch https://www.elastic.co/downloads/elasticsearch

Let us assume ELASTICSEARCH_HOME = C:\elasticsearch-6.2.4

Prerequisite-4: Configure ElasticSearch Cluster

Open ${ELASTICSEARCH_HOME}\config\elasticsearch.yml and add the following configuration
${ELASTICSEARCH_HOME}\config\elasticsearch.yml
cluster.name: springboot-EScluster

Prerequisite-5: Start Elasticsearch Instance. bin\elasticsearch.bat on Windows
