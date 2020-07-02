package com.wavemaker.connector.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.wavemaker.connector.service.MongoDBService;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 3/6/20
 */
@Service
@Primary
public class MongoDBQueryConnectorImpl implements MongoDBQueryConnector {

    @Autowired
    MongoDBService mongoDBService;

    @Override
    public List<String> getAllCollections() {
        return mongoDBService.listCollectionNames();
    }
}
