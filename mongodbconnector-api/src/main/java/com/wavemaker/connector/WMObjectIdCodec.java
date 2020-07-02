package com.wavemaker.connector;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 21/5/20
 */
public class WMObjectIdCodec implements Codec<ObjectId> {

    @Override
    public void encode(final BsonWriter writer, final ObjectId value, final EncoderContext encoderContext) {
        writer.writeObjectId(value);
    }

    @Override
    public ObjectId decode(final BsonReader reader, final DecoderContext decoderContext) {
        return reader.readObjectId();
    }

    @Override
    public Class<ObjectId> getEncoderClass() {
        return ObjectId.class;
    }
}