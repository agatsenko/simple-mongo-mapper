/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2019-12-17
 */
package simple.mongo;

import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import static com.mongodb.client.model.Filters.*;
import static simple.mongo.SimpleDocMapper.INT_VAL_KEY;
import static simple.mongo.SimpleDocMapper.STR_AS_BOOL_VAL_KEY;

public class SimpleDocAccessorRepo extends AbstractDocumentAccessorRepo<String, SimpleDocAccessor, SimpleDocMapper> {
    public SimpleDocAccessorRepo(MongoDatabase mongoDb) {
        super(SimpleDocMapper.instance, mongoDb);
    }

    public MongoIterable<SimpleDocAccessor> findByIntValAndStrAsBoolVal(int intVal, boolean strAsBoolVal) {
        return getCollection().find(
                and(
                        eq(INT_VAL_KEY, intVal),
                        eq(STR_AS_BOOL_VAL_KEY, mapper.getvalueConverter(STR_AS_BOOL_VAL_KEY).convert(strAsBoolVal))
                )
        ).map(SimpleDocAccessor::new);
    }

    public MongoIterable<SimpleDocAccessor> findByIntValOrStrAsBoolVal(int intVal, boolean strAsBoolVal) {
        return getCollection().find(
                or(
                        eq(INT_VAL_KEY, intVal),
                        eq(STR_AS_BOOL_VAL_KEY, mapper.getvalueConverter(STR_AS_BOOL_VAL_KEY).convert(strAsBoolVal))
                )
        ).map(SimpleDocAccessor::new);
    }

    @Override
    protected SimpleDocAccessor createAccessor(Document doc) {
        return new SimpleDocAccessor(doc);
    }
}
