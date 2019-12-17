package simple.mongo;

import com.google.common.collect.ImmutableMap;
import simple.mongo.mapper.*;
import simple.util.ValueConverters;

public class SimpleDocMapper extends AbstractDocumentMapper {
    public static final String COLLECTION_NAME = "simple_docs";

    public static final String INT_VAL_KEY = "intVal";
    public static final String STR_AS_BOOL_VAL_KEY = "strAsBoolVal";

    public static final SimpleDocMapper instance = new SimpleDocMapper();

    private SimpleDocMapper() {
        super(
                ImmutableMap.<String, FieldReader<?>>builder()
                        .put(DOCUMENT_ID_KEY, FieldReaders.strReader)
                        .put(DOCUMENT_CLASS_KEY, documentClassReader)
                        .put(INT_VAL_KEY, FieldReaders.intReader)
                        .put(STR_AS_BOOL_VAL_KEY, FieldReaders.strAsBoolReader)
                        .build(),
                ImmutableMap.<String, FieldWriter<?, ?>>builder()
                        .put(DOCUMENT_ID_KEY, FieldWriter.directWriter())
                        .put(DOCUMENT_CLASS_KEY, documentClassWriter)
                        .put(INT_VAL_KEY, FieldWriter.directWriter())
                        .put(
                                STR_AS_BOOL_VAL_KEY,
                                FieldWriter.of(ValueConverters.boolAsStrConverter, FieldSetters.directSetter())
                        )
                        .build()
        );
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }
}
