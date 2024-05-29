package com.example.auth.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MyCustomWriter implements ItemWriter<String> {
    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        for (String data : chunk) {
            System.out.println("MyCustomWriter    : Writing data    : " + data);
        }
        System.out.println("MyCustomWriter    : Writing data    : completed");
    }
}
