package ua.lifebook.web.application;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

public class ElasticTest {

    // How to run local Elasticsearch:
    // docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2

    public static void main(String[] args) throws Exception {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
//            send(client);
//            search(client);
            searchWithQueryBuilder(client);
//            getByIdAndDelete(client);
        }
    }

    private static void send(RestHighLevelClient client) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
            .field("fullName", "Peter2")
            .field("dateOfBirth", LocalDate.of(2000, 1, 1))
            .field("age", 21)
            .endObject();

        IndexRequest indexRequest = new IndexRequest("people");
        indexRequest.source(builder);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    private static void search(RestHighLevelClient client) throws IOException {
        runQuery(client, new SearchRequest());
    }

    private static void searchWithQueryBuilder(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
//            .postFilter(QueryBuilders.matchAllQuery());
//            .postFilter(QueryBuilders.matchQuery("fullName", "Peter"));
//            .postFilter(QueryBuilders.rangeQuery("age").from(15).to(50));
//            .postFilter(QueryBuilders.multiMatchQuery("0", "age", "dateOfBirth^2")); // ^<n> - sets priority in search results -- doesn't work
            .query(
                new FunctionScoreQueryBuilder(rangeQuery("age").from(10).to(29))
            );
//            .postFilter(QueryBuilders.simpleQueryStringQuery("+John -Doe OR Janette")); // lucene syntax

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);

        runQuery(client, searchRequest);
    }

    private static void runQuery(RestHighLevelClient client, SearchRequest searchRequest) throws IOException {
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = response.getHits().getHits();
        List<String> results = Arrays.stream(searchHits)
            .map(fields -> {
                System.out.println(fields.getId());
                return fields.getSourceAsString();
            })
            .collect(Collectors.toList());
        System.out.println(results);
    }

    private static void getByIdAndDelete(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest("people");
        final String[] strings = getRequest.storedFields();
        System.out.println("fields: " + strings);
        getRequest.id("id");

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        // process fields

        DeleteRequest deleteRequest = new DeleteRequest("people");
//        deleteRequest.id("lJ3xhHUBBsO1CWmhgOkA");
//        deleteRequest.id("lZ0AhXUBBsO1CWmhVOnC");
        deleteRequest.id("lp0FhXUBBsO1CWmh6OkO");
//        deleteRequest.id("l50OhXUBBsO1CWmh3-mv");

        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    private static RestHighLevelClient getRestHighLevelClient() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        return new RestHighLevelClient(restClientBuilder);
    }
}
