package simple.mongo.mapper;

import org.bson.Document;

@FunctionalInterface
public interface FieldReader<T> {
    T read(Document doc, String key);
}
