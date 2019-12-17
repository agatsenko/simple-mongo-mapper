package simple.mongo.mapper;

import org.bson.Document;
import org.bson.types.ObjectId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FieldReaders {
    public static final FieldReader<Integer> intReader = Document::getInteger;

    public static final FieldReader<String> strReader = Document::getString;

    public static final FieldReader<Boolean> strAsBoolReader = (doc, key) -> {
        final String str = doc.getString(key);
        return str == null ? null : "1".equals(str);
    };
}
