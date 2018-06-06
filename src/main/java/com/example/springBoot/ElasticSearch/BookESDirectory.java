package com.example.springBoot.ElasticSearch;

import com.example.springBoot.ElasticSearch.entity.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookESDirectory extends ElasticSearchClient {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected static final String DEFAULT_IP_ADDRESS = "http://localhost:11200";
    public static final String DEFAULT_INDEX = "book";
    public static final String DEFAULT_TYPE = "general";

    public BookESDirectory() {
        super(DEFAULT_IP_ADDRESS, DEFAULT_INDEX, DEFAULT_TYPE);
    }

    public boolean index(Book book) throws Exception {
        String bookJson = gson.toJson(book);
        String bookKey = Long.toString(book.getId());

        Index indexRequest = new Index.Builder(bookJson).index(getIndex()).type(getType()).id(bookKey).build();
        JestResult indexResponse = this.execute(indexRequest);

        return indexResponse.isSucceeded();
    }

    public List<Book> search(String searchKey) throws Exception {
        List<Book> bookList = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", searchKey));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                // multiple index or types can be added.
                .addIndex(DEFAULT_INDEX)
                .addType(DEFAULT_TYPE)
                .build();

        // execute the query
        SearchResult result = this.execute(search);

        // extract IDs from the results
        if (result.isSucceeded()) {
            JsonArray jsonArray = result.getJsonObject().get("hits").getAsJsonObject().get("hits").getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                JsonObject jsonObject = elem.getAsJsonObject();
                bookList.add(fromJson(jsonObject.get("_source").toString()));
            }
        }
        return bookList;
    }

    private static Book fromJson(String bookJson) {
        return gson.fromJson(bookJson, Book.class);
    }

}
