**This is a small project to implement search using Spring Boot, Elastic Search and Java Jest.**

# To run this demo, follow the below steps

* Install Java and set JAVA_HOME and PATH variables.

* Install Maven.

* Install Elasticsearch https://www.elastic.co/downloads/elasticsearch

    Set ELASTICSEARCH_HOME = C:\elasticsearch-6.2.4

* Configure ElasticSearch Cluster:

    Open ${ELASTICSEARCH_HOME}\config\elasticsearch.yml and add the following configuration    ${ELASTICSEARCH_HOME}\config\elasticsearch.yml
    cluster.name: springboot-EScluster

* Start Elasticsearch Instance by running the bin\elasticsearch.bat on Windows
