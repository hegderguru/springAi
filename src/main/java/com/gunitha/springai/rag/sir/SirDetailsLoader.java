package com.gunitha.springai.rag.sir;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SirDetailsLoader {

    @Autowired
    VectorStore vectorStore;

    @Value("classpath:sir/S10_87_162__Government Kannada and English Medium Model Center School Room No 3_Karur_03_08_2026_17_20_54.pdf")
    Resource resource;

    //@PostConstruct
    public void init() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        List<Document> documents = tikaDocumentReader.get();
        TextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(500).build();
        vectorStore.add(textSplitter.split(documents));
    }

}
