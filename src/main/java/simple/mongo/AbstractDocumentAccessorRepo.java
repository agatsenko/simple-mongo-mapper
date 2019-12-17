package simple.mongo;

import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import simple.mongo.mapper.AbstractDocumentMapper;
import simple.util.Check;

import static simple.mongo.mapper.AbstractDocumentMapper.DOCUMENT_ID_KEY;

public abstract class AbstractDocumentAccessorRepo<
        TDocId,
        TAccessor extends AbstractDocumentAccessor<TDocId, TAccessor>,
        TMapper extends AbstractDocumentMapper> {
    protected final TMapper mapper;
    protected final MongoDatabase mongoDb;

    public AbstractDocumentAccessorRepo(TMapper mapper, MongoDatabase mongoDb) {
        Check.argNotNull(mapper, "mapper");
        Check.argNotNull(mongoDb, "mongoDb");

        this.mapper = mapper;
        this.mongoDb = mongoDb;
    }

    public Optional<TAccessor> findById(TDocId id) {
        return Optional.ofNullable(getCollection().find(docIdFilter(id)).first()).map(this::createAccessor);
    }

    public MongoIterable<TAccessor> findAll() {
        return getCollection().find().map(this::createAccessor);
    }

    public void insert(TAccessor accessor) {
        getCollection().insertOne(accessor.getDocument());

    }

    public UpdateResult update(TAccessor accessor) {
        return getCollection().replaceOne(
                docIdFilter(accessor),
                accessor.getDocument(),
                new ReplaceOptions().upsert(false)
        );
    }

    public DeleteResult remove(TDocId id) {
        return getCollection().deleteOne(docIdFilter(id));
    }

    public DeleteResult remove(TAccessor accessor) {
        return getCollection().deleteOne(docIdFilter(accessor));
    }

    protected abstract TAccessor createAccessor(Document doc);

    protected MongoCollection<Document> getCollection() {
        return mongoDb.getCollection(mapper.getCollectionName());
    }

    protected Bson docIdFilter(TDocId id) {
        return Filters.eq(DOCUMENT_ID_KEY, id);
    }

    protected Bson docIdFilter(TAccessor accessor) {
        return Filters.eq(DOCUMENT_ID_KEY, accessor.getDocumentId().get());
    }
}
