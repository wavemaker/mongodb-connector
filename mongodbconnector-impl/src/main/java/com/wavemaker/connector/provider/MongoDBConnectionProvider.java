package com.wavemaker.connector.provider;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 7/5/20
 */

@Service
public class MongoDBConnectionProvider {

    @Value("${connection.url}")
    private String connectionURL;

    @Value("${database.name}")
    private String dbName;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    public boolean testConnection() {
        try {
            ConnectionString connectionString = new ConnectionString(connectionURL);
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();

            com.mongodb.client.MongoClient mongoClient = MongoClients.create(clientSettings);
            MongoDatabase db = mongoClient.getDatabase(dbName);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to acquire connection from MongoDB", e);
        }
    }

    public MongoDatabase getMondoDbConnection() {
        try {
            ConnectionString connectionString = new ConnectionString(connectionURL);
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    pojoCodecRegistry);
            MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .credential(credential)
                    .codecRegistry(codecRegistry)
                    .build();

            com.mongodb.client.MongoClient mongoClient = MongoClients.create(clientSettings);
            MongoDatabase db = mongoClient.getDatabase(dbName);
            return db;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to acquire connection with MongoDB", e);
        }
    }


}
