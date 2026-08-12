package com.gunitha.springai.controller.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;

import java.util.List;

public class PIIMaskingDocumentPostProcessor implements DocumentPostProcessor {
    @Override
    public List<Document> process(Query query, List<Document> documents) {
        return documents.stream().map(document -> {
            String redactedText = document.getText().replaceAll("Death", "D****");
            return Document.builder().text(redactedText).build();
        }).toList();
    }

    @Override
    public List<Document> apply(Query query, List<Document> documents) {
        return DocumentPostProcessor.super.apply(query, documents);
    }
}
