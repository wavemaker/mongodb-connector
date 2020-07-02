package com.wavemaker.connector.mongodb;

import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wavemaker.connector.model.MongoBaseEntity;
import com.wavemaker.connector.service.MongoDBService;


@Service
@Primary
public class MongoDBConnectorImpl implements MongoDBConnector {

    @Autowired
    MongoDBService mongoDBService;

    @Override
    public boolean testConnection() {
        return mongoDBService.testConnection();
    }

    @Override
    public List<String> listCollections() {
        return mongoDBService.listCollectionNames();
    }

    @Override
    public void createCollection(String collectionName) {
        mongoDBService.createCollection(collectionName);
    }

    @Override
    public Page<MongoBaseEntity> listDocuments(String collectionName, Class mongoEntityClass, Pageable pageable){
        return mongoDBService.getCollection(collectionName, mongoEntityClass,pageable);
    }

    @Override
    public void dropCollection(String collectionName) {
        mongoDBService.dropCollection(collectionName);
    }

    public void addDocument(String collectionName, MongoBaseEntity mongoBaseEntity) {
        mongoDBService.addDocument(collectionName, mongoBaseEntity);
    }

    @Override
    public void addDocuments(String collectionName, List<? extends MongoBaseEntity> mongoEntities) {
        mongoDBService.addDocuments(collectionName, mongoEntities);
    }

    @Override
    public void deleteDocument(String collectionName, MongoBaseEntity mongoBaseEntity) {
        mongoDBService.deleteDocument(collectionName, mongoBaseEntity);
    }

    @Override
    public void updateDocument(String collectionName, MongoBaseEntity mongoBaseEntity, MongoBaseEntity newMongoBaseEntity) {
        mongoDBService.updateDocument(collectionName, mongoBaseEntity, newMongoBaseEntity);
    }

    @Override
    public MongoBaseEntity findDocument(String collectionName, Bson var, Class responseEntityClass) {
        return mongoDBService.findDocument(collectionName, var, responseEntityClass);
    }

    @Override
    public MongoBaseEntity getDocumentById(String collectionName, String  objectId) {
        return mongoDBService.findDocumentById(collectionName, objectId);
    }


}