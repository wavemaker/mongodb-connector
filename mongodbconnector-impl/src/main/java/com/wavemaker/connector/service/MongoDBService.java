package com.wavemaker.connector.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.wavemaker.connector.model.MongoBaseEntity;
import com.wavemaker.connector.provider.MongoDBConnectionProvider;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 7/5/20
 */
@Service
public class MongoDBService {

    @Autowired
    MongoDBConnectionProvider mongoDBConnectionProvider;

    MongoDatabase dbconnection;

    public boolean testConnection() {
        return mongoDBConnectionProvider.testConnection();
    }

    public List<String> listCollectionNames() {
        MongoIterable<String> strings = getDbconnection().listCollectionNames();
        List<String> results = new ArrayList<>();
        MongoCursor<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            results.add(iterator.next());
        }
        return results;
    }

    public void createCollection(String collectionName) {
        getDbconnection().createCollection(collectionName);
    }

    public void dropCollection(String collectionName) {
        getDbconnection().getCollection(collectionName).drop();
    }

    public Page<MongoBaseEntity> getCollection(String collectionName, Class ModelEntityClass, Pageable pageable) {
        MongoCollection<? extends MongoBaseEntity> collection = getDbconnection().getCollection(collectionName, ModelEntityClass);
        FindIterable<? extends MongoBaseEntity> mongoEntities = collection.find();
        List<MongoBaseEntity> entities = toList(mongoEntities);
        return new PageImpl<>(entities, pageable, entities.size());
    }

    private List<MongoBaseEntity> toList(FindIterable<? extends MongoBaseEntity> mongoEntities) {
        List<MongoBaseEntity> entities = new ArrayList<>();
        MongoCursor<? extends MongoBaseEntity> cursor = mongoEntities.cursor();
        while (cursor.hasNext()) {
            entities.add(cursor.next());
        }
        return entities;
    }

    public void addDocument(String collectionName, MongoBaseEntity mongoBaseEntity) {
        getDbconnection().getCollection(collectionName, MongoBaseEntity.class).insertOne(mongoBaseEntity);
    }

    public void addDocuments(String collectionName, List<? extends MongoBaseEntity> mongoEntity) {
        getDbconnection().getCollection(collectionName, MongoBaseEntity.class).insertMany(mongoEntity);
    }

    public void deleteDocument(String collectionName, MongoBaseEntity mongoBaseEntity) {
        Document filterByObjectId = new Document("_id", mongoBaseEntity.getId());
        getDbconnection().getCollection(collectionName, MongoBaseEntity.class).deleteOne(filterByObjectId);
    }

    public void updateDocument(String collectionName, MongoBaseEntity mongoBaseEntity, MongoBaseEntity newMongoBaseEntity) {
        Document filterByObjectId = new Document("_id", mongoBaseEntity.getId());
        getDbconnection().getCollection(collectionName, MongoBaseEntity.class).findOneAndReplace(filterByObjectId, newMongoBaseEntity);
    }

    public MongoBaseEntity findDocumentById(String collectionName, String objectId) {
        Document filterByObjectId = new Document("_id", new ObjectId(objectId));
        MongoCollection<MongoBaseEntity> collection = getDbconnection().getCollection(collectionName, MongoBaseEntity.class);
        MongoCursor<MongoBaseEntity> cursor = collection.find(filterByObjectId).cursor();
        if (cursor.hasNext()) {
            return cursor.next();
        }
        throw new MongoException("Document not found with the given id " + objectId);
    }

    public MongoBaseEntity findDocument(String collectionName, Bson var, Class modelEntityClass) {
        return (MongoBaseEntity) getDbconnection().getCollection(collectionName, modelEntityClass).find(var).first();
    }

    private MongoDatabase getDbconnection() {
        if (dbconnection == null) {
            dbconnection = mongoDBConnectionProvider.getMondoDbConnection();
        }
        return dbconnection;
    }
}
