package simple.mongo.mapper;

import org.bson.Document;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FieldSetters {
    private static final FieldSetter directSetter = Document::put;

    @SuppressWarnings("unchecked")
    public static <T> FieldSetter<T> directSetter() {
        return (FieldSetter<T>) directSetter;
    }
}
