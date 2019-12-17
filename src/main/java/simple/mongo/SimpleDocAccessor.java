package simple.mongo;

import java.util.Optional;

import org.bson.Document;
import simple.mongo.mapper.AbstractDocumentMapper;

import static simple.mongo.SimpleDocMapper.INT_VAL_KEY;
import static simple.mongo.SimpleDocMapper.STR_AS_BOOL_VAL_KEY;

public class SimpleDocAccessor extends AbstractDocumentAccessor<String, SimpleDocAccessor> {
    private static final SimpleDocMapper mapper = SimpleDocMapper.instance;

    public SimpleDocAccessor(Document document) {
        super(SimpleDocAccessor.class, document);
    }

    public static SimpleDocAccessor createNew() {
        return new SimpleDocAccessor(new Document());
    }

    public Optional<Integer> getIntVal() {
        return mapper.readOpn(document, INT_VAL_KEY);
    }

    public SimpleDocAccessor setIntVal(Integer val) {
        mapper.write(document, INT_VAL_KEY, val);
        return this;
    }

    public Optional<Boolean> getStrAsBoolVal() {
        return mapper.readOpn(document, STR_AS_BOOL_VAL_KEY);
    }

    public SimpleDocAccessor setStrAsBoolVal(Boolean val) {
        mapper.write(document, STR_AS_BOOL_VAL_KEY, val);
        return this;
    }

    @Override
    protected AbstractDocumentMapper getMapper() {
        return mapper;
    }
}
