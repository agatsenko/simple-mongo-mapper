package simple.mongo.mapper;

import org.bson.Document;

@FunctionalInterface
public interface FieldSetter<T> {
    void set(Document doc, String key, T value);
}
