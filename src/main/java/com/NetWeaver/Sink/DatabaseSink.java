package com.NetWeaver.Sink;

import com.NetWeaver.Models.PageData;
import com.NetWeaver.Storage.Converter.PageEntityConverter;
import com.NetWeaver.Storage.Entity.PageEntity;
import com.NetWeaver.Storage.Repository.PageRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSink implements PageSink {

    private final PageRepository repository;
    private final PageEntityConverter converter;

    public DatabaseSink(PageRepository repository, PageEntityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void handle(PageData pageData) {
        PageEntity entity = converter.toEntity(pageData);
        repository.save(entity);
    }
}
