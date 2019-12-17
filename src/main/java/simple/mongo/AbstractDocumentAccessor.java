package simple.mongo;

import java.util.Optional;

import org.bson.Document;
import simple.mongo.mapper.*;
import simple.util.Check;
import simple.util.ValueConverters;

import static simple.mongo.mapper.AbstractDocumentMapper.DOCUMENT_CLASS_KEY;
import static simple.mongo.mapper.AbstractDocumentMapper.DOCUMENT_ID_KEY;

public abstract class AbstractDocumentAccessor<TDocId, TThis extends AbstractDocumentAccessor<TDocId, TThis>> {
    private final Class<TThis> thisClass;
    protected final Document document;

    public AbstractDocumentAccessor(Class<TThis> thisClass, Document document) {
        Check.argNotNull(thisClass, "thisClass");
        Check.arg(thisClass == getClass(), "thisClass is not equal to this class");
        Check.argNotNull(document, "document");

        this.thisClass = thisClass;
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public Optional<TDocId> getDocumentId() {
        return getMapper().readOpn(document, DOCUMENT_ID_KEY);
    }

    public TThis setDocumentId(TDocId documentId) {
        getMapper().write(document, DOCUMENT_ID_KEY, documentId);
        return thisClass.cast(this);
    }

    public Optional<String> getDocumentClass() {
        return getMapper().readOpn(document, DOCUMENT_CLASS_KEY);
    }

    public TThis setDocumentClass(String documentClass) {
        getMapper().write(document, DOCUMENT_CLASS_KEY, documentClass);
        return thisClass.cast(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return document.equals(((AbstractDocumentAccessor<?, ?>) obj).document);
    }

    @Override
    public int hashCode() {
        return document.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + document + ")";
    }

    protected abstract AbstractDocumentMapper getMapper();
}
