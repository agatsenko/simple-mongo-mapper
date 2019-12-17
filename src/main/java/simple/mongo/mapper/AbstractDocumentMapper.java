package simple.mongo.mapper;

import java.util.Map;
import java.util.Optional;

import org.bson.Document;
import com.google.common.collect.ImmutableMap;
import simple.util.Check;
import simple.util.ValueConverter;
import simple.util.ValueConverters;

public abstract class AbstractDocumentMapper {
    public static final String DOCUMENT_ID_KEY = "_id";
    public static final String DOCUMENT_CLASS_KEY = "_class";

    protected static final FieldReader<String> documentClassReader = FieldReaders.strReader;
    protected static final FieldWriter<String, String> documentClassWriter = FieldWriter.directWriter();

    private final Map<String, FieldReader<?>> fieldReaders;
    private final Map<String, FieldWriter<?, ?>> fieldWriters;

    public AbstractDocumentMapper(
            Map<String, FieldReader<?>> fieldReaders,
            Map<String, FieldWriter<?, ?>> fieldWriters) {
        this.fieldReaders = ImmutableMap.<String, FieldReader<?>>builder()
                .putAll(fieldReaders)
                .build();
        this.fieldWriters = ImmutableMap.<String, FieldWriter<?, ?>>builder()
                .putAll(fieldWriters)
                .build();
    }

    public abstract String getCollectionName();

    public <T> T read(Document doc, String key) {
        @SuppressWarnings("unchecked")
        final FieldReader<T> reader = (FieldReader<T>) fieldReaders.get(key);
        Check.state(reader != null, "unable to find field reader for '%s' field", key);
        return reader.read(doc, key);
    }

    public <T> Optional<T> readOpn(Document doc, String key) {
        return Optional.of(read(doc, key));
    }

    @SuppressWarnings("unchecked")
    public void write(Document doc, String key, Object value) {
        ensureFindWriter(key).write(doc, key, value);
    }

    public <V> void writeOpn(Document doc, String key, Optional<V> valueOpn) {
        write(doc, key, valueOpn.orElse(null));
    }

    public <V, F> ValueConverter<V, F> getvalueConverter(String key) {
        @SuppressWarnings("unchecked")
        FieldWriter<V, F> writer = (FieldWriter<V, F>) ensureFindWriter(key);
        return writer.getConverter();
    }

    private FieldWriter ensureFindWriter(String key) {
        final FieldWriter writer = fieldWriters.get(key);
        Check.state(writer != null, "unable to find field writer for '%s' field", key);
        return writer;
    }
}
