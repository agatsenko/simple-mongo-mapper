package simple.mongo.mapper;

import org.bson.Document;
import simple.util.ValueConverter;
import simple.util.ValueConverters;

public class FieldWriter<V, F> {
    private static final FieldWriter directWriter = FieldWriter.of(
            ValueConverters.directConverter(),
            FieldSetters.directSetter()
    );

    private final ValueConverter<V, F> converter;
    private final FieldSetter<F> setter;

    public FieldWriter(ValueConverter<V, F> converter, FieldSetter<F> setter) {
        this.converter = converter;
        this.setter = setter;
    }

    @SuppressWarnings("unchecked")
    public static <V, F> FieldWriter<V, F> directWriter() {
        return (FieldWriter<V, F>) directWriter;
    }

    public static <V, F> FieldWriter<V, F> of(ValueConverter<V, F> converter, FieldSetter<F> setter) {
        return new FieldWriter<>(converter, setter);
    }

    public ValueConverter<V, F> getConverter() {
        return converter;
    }

    public FieldSetter<F> getSetter() {
        return setter;
    }

    public void write(Document doc, String key, V value) {
        setter.set(doc, key, converter.convert(value));
    }
}
