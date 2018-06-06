package com.example.springBoot.ElasticSearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class ElasticSearchClient {
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchClient.class);

    private static JestClientFactory factory;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected static final String DEFAULT_IP_ADDRESS = "http://localhost:9200";
    private JestClient client;

    private String ipAddress;
    private String index;
    private String type;

    public ElasticSearchClient(String index, String type) {
        this.ipAddress = DEFAULT_IP_ADDRESS;
        this.index = index;
        this.type = type;
    }

    protected void init() {
        if (factory == null) {
            factory = new JestClientFactory();
            factory.setHttpClientConfig(new HttpClientConfig
                    .Builder(getIpAddress())
                    .multiThreaded(true)
                    //Per default this implementation will create no more than 2 concurrent connections per given route
                    .defaultMaxTotalConnectionPerRoute(2)
                    // and no more 20 connections in total
                    .maxTotalConnection(5)
                    .build());
        }
        if (client == null) {
            client = factory.getObject();
        }
    }

    public JestResult get(String id) throws Exception {
        Get getRequest = new Get.Builder(index, id).build();
        JestResult getResponse = this.execute(getRequest);
        return getResponse;
    }

    protected <T extends JestResult> T execute(Action<T> clientRequest) throws Exception {
        logRequest(clientRequest);
        T result = client.execute(clientRequest);
        logResponse(result);
        return result;
    }

    private <T extends JestResult> void logRequest(Action<T> request) {
        StringBuilder requestStringBuilder = new StringBuilder();
        requestStringBuilder.append("================ Elasticsearch HTTP REQ ================" + "\n");
        requestStringBuilder.append(request.getRestMethodName()).append(" ").append(request.getURI()).append("\n");
        String data;
        try {
            data = pretty(request.getData(gson));
        } catch (Exception e) {
            data = gson.toJson(request.getData(gson));
        }
        requestStringBuilder.append(data).append("\n");
        log.info(requestStringBuilder.toString());
    }

    private <T extends JestResult> void logResponse(T response) {
        try {
            StringBuilder responseStringBuilder = new StringBuilder();
            responseStringBuilder.append("================ Elastic Search HTTP RES ================" + "\n");
            responseStringBuilder.append(pretty(response.getJsonString())).append("\n");
            log.info(responseStringBuilder.toString());
        } catch (Exception e) {
            log.error("Exception logging Elastic Search response");
        }
    }

    public String pretty(String jsonStr) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonStr);
        return gson.toJson(jsonElement);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
