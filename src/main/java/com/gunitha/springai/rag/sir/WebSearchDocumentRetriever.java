package com.gunitha.springai.rag.sir;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebSearchDocumentRetriever implements DocumentRetriever {

    private static final String TAVILY_API_KEY = "TAVILY_SEARCH_API_KEY";
    private static final String TAVILY_BASE_URL = "https://api.tavily.com/search";
    private static final int DEFAULT_RESULT_LIMIT = 5;

    private final int resultLimit;
    private final RestClient restClient;

    private WebSearchDocumentRetriever(RestClient.Builder restClientBuilder, int resultLimit) {
        String apiKey = System.getenv(TAVILY_API_KEY);
        Assert.notNull(apiKey, "TAVILY_API_KEY environment variable is required");
        this.restClient = restClientBuilder
                .baseUrl(TAVILY_BASE_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();
        if (resultLimit <= 0) {
            resultLimit = DEFAULT_RESULT_LIMIT;
        }
        this.resultLimit = resultLimit;
    }

    @Override
    public List<Document> retrieve(Query query) {
        log.info("Retrieve documents for query: {}", query);
        TavilyResponsePayload tavilyResponsePayload = restClient.post()
                .body(new TavilyRequestPayload(query.text(), "advanced", resultLimit))
                .retrieve()
                .body(TavilyResponsePayload.class);
        assert tavilyResponsePayload != null;
        ArrayList<Document> documents = new ArrayList<>(tavilyResponsePayload.results().size());

        tavilyResponsePayload.results().stream().map(hit -> {
            return Document.builder()
                    .text(hit.content())
                    .metadata("title", hit.title())
                    .metadata("url", hit.url())
                    .score(hit.score())
                    .build();
        }).forEach(documents::add);
        return documents;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TavilyRequestPayload(String query, String searchDepth, int maxResults) {
    }

    record TavilyResponsePayload(List<Hit> results) {
        record Hit(String title, String url, String content, Double score) {
        }
    }

    @Override
    public List<Document> apply(Query query) {
        return DocumentRetriever.super.apply(query);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        RestClient.Builder restClientBuilder;
        private int resultLimit = DEFAULT_RESULT_LIMIT;

        public Builder restClientBuilder(RestClient.Builder restClientBuilder) {
            this.restClientBuilder = restClientBuilder;
            return this;
        }

        public Builder maxResults(int resultLimit) {
            if (resultLimit <= 0) {
                resultLimit = DEFAULT_RESULT_LIMIT;
            }
            this.resultLimit = resultLimit;
            return this;
        }

        public WebSearchDocumentRetriever build() {
            return new WebSearchDocumentRetriever(restClientBuilder, resultLimit);
        }
    }

}
