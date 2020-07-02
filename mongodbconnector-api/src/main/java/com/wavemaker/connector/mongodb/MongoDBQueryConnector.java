package com.wavemaker.connector.mongodb;

import java.util.List;

import com.wavemaker.runtime.connector.annotation.WMConnector;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 3/6/20
 */
@WMConnector(name = "mongodb",
        description = "A simple connector mongodb that can be used in WaveMaker application")
public interface MongoDBQueryConnector {

    public List<String> getAllCollections();
}
