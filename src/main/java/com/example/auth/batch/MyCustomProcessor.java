package com.example.auth.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MyCustomProcessor implements ItemProcessor<String, String> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String process(String data) throws Exception {
        logger.info("MyCustomProcessor : Processing data : "+data);
        data = data.toUpperCase();
        return data;
    }
}
