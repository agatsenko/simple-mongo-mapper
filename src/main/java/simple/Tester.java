package simple;

import java.util.UUID;
import java.util.function.Consumer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import simple.mongo.SimpleDocAccessor;
import simple.mongo.SimpleDocAccessorRepo;

public class Tester {
    public static void main(String[] args) {
        try (final MongoClient mongoClient = createMongoClient()) {
            final MongoDatabase mongoDb = mongoClient.getDatabase("test");
            final SimpleDocAccessorRepo repo = new SimpleDocAccessorRepo(mongoDb);
            for (SimpleDocAccessor accessor : repo.findAll()) {
                repo.remove(accessor);
            }
            for (int i = 0; i < 10; ++i) {
                SimpleDocAccessor accessor = SimpleDocAccessor
                        .createNew()
                        .setDocumentId(UUID.randomUUID().toString())
                        .setIntVal(i)
                        .setStrAsBoolVal((i & 1) == 0);
                repo.insert(accessor);
            }
            System.out.println("================================================");
            repo
                    .findAll()
                    .forEach((Consumer<SimpleDocAccessor>) (System.out::println));
            System.out.println("================================================");
            repo
                    .findByIntValAndStrAsBoolVal(1, false)
                    .forEach((Consumer<SimpleDocAccessor>) (System.out::println));
            System.out.println("================================================");
            repo
                    .findByIntValOrStrAsBoolVal(1, true)
                    .forEach((Consumer<SimpleDocAccessor>) (System.out::println));
            System.out.println("================================================");
            repo
                    .findByIntValOrStrAsBoolVal(1, true)
                    .forEach((Consumer<SimpleDocAccessor>) (accessor -> {
                        accessor.setStrAsBoolVal(!accessor.getStrAsBoolVal().get());
                        repo.update(accessor);
                    }));
            repo
                    .findByIntValOrStrAsBoolVal(1, true)
                    .forEach((Consumer<SimpleDocAccessor>) (System.out::println));
            System.out.println("================================================");
        }
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                        .credential(
                                MongoCredential.createCredential(
                                        "test",
                                        "test",
                                        "test".toCharArray()
                                )
                        )
                        .build()
        );
    }
}
