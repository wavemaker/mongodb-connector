package com.wavemaker.connector.mongodb;

import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.connector.model.MongoBaseEntity;
import com.wavemaker.runtime.connector.annotation.WMConnector;


@WMConnector(name = "mongodb",
        description = "A simple connector mongodb that can be used in WaveMaker application")
public interface MongoDBConnector {

    public boolean testConnection();

    public List<String> listCollections();
     
    public void createCollection(String collectionName);

    public void dropCollection(String collectionName);

    public Page<MongoBaseEntity> listDocuments(String collectionName, Class mongoEntityClass, Pageable pageable);

    public void addDocument(String collectionName, MongoBaseEntity mongoBaseEntity);
     
    public void addDocuments(String collectionName, List<? extends MongoBaseEntity> mongoEntities);
     
    public void deleteDocument(String collectionName, MongoBaseEntity mongoBaseEntity);
     
    public void updateDocument(String collectionName, MongoBaseEntity mongoBaseEntity, MongoBaseEntity newMongoBaseEntity);
     
    public MongoBaseEntity findDocument(String collectionName, Bson var, Class responseEntityClass);

    public MongoBaseEntity getDocumentById(String collectionName, String  objectId);

}