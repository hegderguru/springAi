package com.gunitha.springai.config.rag;

import org.springframework.ai.chat.cache.semantic.SemanticCache;
import org.springframework.ai.chat.cache.semantic.SemanticCacheAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.vectorstore.redis.cache.semantic.DefaultSemanticCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SemanticCacheConfig {

    @Bean("cachePgVectorStore")
    public PgVectorStore cachePgVectorStore(
            JdbcTemplate jdbcTemplate,
            @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {

        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .dimensions(1024) // Change this to exactly 1024 to match your Ollama model
                .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)
                .indexType(PgVectorStore.PgIndexType.HNSW)
                .initializeSchema(true) // This will create the table only if it does not exist
                .build();
    }

    @Bean
    SemanticCache semanticCache(PgVectorStore cachePgVectorStore,
                                @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        return DefaultSemanticCache.builder()
                .vectorStore(cachePgVectorStore)
                .embeddingModel(embeddingModel)
                .similarityThreshold(0.9)
                .build();
    }

    @Bean
    public SemanticCacheAdvisor semanticCacheAdvisor(SemanticCache semanticCache) {
        return SemanticCacheAdvisor.builder().cache(semanticCache).build();
    }
}
