package com.wavemaker.connector.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 11/5/20
 */
public abstract class MongoBaseEntity implements Serializable {


    protected ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
