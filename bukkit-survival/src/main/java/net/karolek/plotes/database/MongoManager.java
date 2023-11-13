package net.karolek.plotes.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public class MongoManager {

    private final String uri;
    private final String data;

    private MongoClient client;
    private MongoDatabase database;

    private final Executor executor;

    public MongoManager(String uri, String data) {
        this.uri = uri;
        this.data = data;
        this.executor = Executors.newFixedThreadPool(4);
        this.connect();
    }

    private void connect() {
        this.executor.execute(() -> {
            this.client = MongoClients.create(this.uri);
            this.database = this.client.getDatabase(this.data);
            System.out.println("connected to database");
        });
    }

    public void insert(String collection, Document document) {
        this.executor.execute(() -> this.database.getCollection(collection).insertOne(document));
    }

    public <V> void update(String collection, Document document, String key, V value, boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).updateOne(new Document(key, value), new BasicDBObject("$set", document)));
        } else {
            this.database.getCollection(collection).updateOne(new Document(key, value), new BasicDBObject("$set", document));
        }
    }

    public <V> void update(String collection, Document document, String key, V value, String key2, V value2, boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).updateOne(new Document(key, value).append(key2, value2), new BasicDBObject("$set", document)));
        } else {
            this.database.getCollection(collection).updateOne(new Document(key, value), new BasicDBObject("$set", document));
        }
    }

    public void removes(String collection, Document object, boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).deleteMany(object));
            return;
        }
        this.database.getCollection(collection).deleteMany(object);

    }


    public void remove(String collection, Document object, boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).deleteOne(object));
            return;
        }
        this.database.getCollection(collection).deleteOne(object);

    }

    public void remove(String collection, String key, String value, boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).deleteOne(new Document(key, value)));
            return;
        }
        this.database.getCollection(collection).deleteOne(new Document(key, value));

    }

    public void remove(String collection, String key, String value, String key2, String value2,boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).deleteOne(new Document(key, value).append(key2, value2)));
            return;
        }
        this.database.getCollection(collection).deleteOne(new Document(key, value).append(key2, value2));

    }
    public void removes(String collection, String key, String value, String key2, String value2,boolean async) {
        if (async) {
            this.executor.execute(() -> this.database.getCollection(collection).deleteMany(new Document(key, value).append(key2, value2)));
            return;
        }
        this.database.getCollection(collection).deleteMany(new Document(key, value).append(key2, value2));

    }

    public <V> Document find(String collection, String key, V value) {
        return this.database.getCollection(collection).find(new Document(key, value)).first();
    }

    public <V> Document find(String collection, String key, V value, String sortKey, int sortValue, int limit) {
        return this.database.getCollection(collection).find(new Document(key, value)).sort(new Document(sortKey, sortValue)).limit(limit).first();
    }


    public <V> Document find(String collection, String key, V value, String key2, V value2) {
        return this.database.getCollection(collection).find(new Document(key, value).append(key2, value2)).first();
    }

    public MongoCursor<Document> finds(String collection) {
        return this.database.getCollection(collection).find().cursor();
    }


    public <V> MongoCursor<Document> finds(String collection, String key, V value) {
        return this.database.getCollection(collection).find(new Document(key, value)).cursor();
    }

    public <V> MongoCursor<Document> finds(String collection, String key, V value, String key2, V value2) {
        return this.database.getCollection(collection).find(new Document(key, value).append(key2, value2)).cursor();
    }

    public MongoCursor<Document> finds(String collection, Bson bson) {
        return this.database.getCollection(collection).find(bson).cursor();
    }


    public <V> MongoCursor<Document> finds(String collection, String key, V value, String sortKey, int sortValue, int limit) {
        return this.database.getCollection(collection).find(new Document(key, value)).sort(new Document(sortKey, sortValue)).limit(limit).cursor();
    }


    public MongoCursor<Document> finds(String collection, String sortKey, int sortValue, int limit) {
        return this.database.getCollection(collection).find().sort(new Document(sortKey, sortValue)).limit(limit).cursor();
    }

    public <V> MongoCursor<Document> finds(String collection, String key, V value, String key2, V value2, String sortKey, int sortValue, int limit) {
        return this.database.getCollection(collection).find(new Document(key, value).append(key2, value2)).sort(new Document(sortKey, sortValue)).limit(limit).cursor();
    }

    public HashMap<String, Long> getMapString(Document document) {
        final HashMap<String, Long> to = new HashMap<>();
        document.forEach((s, o) -> to.put(s, (Long) o));
        return to;
    }

    public HashMap<UUID, Long> getMapUUID(Document document) {
        final HashMap<UUID, Long> to = new HashMap<>();
        document.forEach((s, o) -> to.put(UUID.fromString(s), (Long) o));
        return to;
    }


    public HashMap<String, Integer> getMapStringInteger(Document document) {
        final HashMap<String, Integer> to = new HashMap<>();
        document.forEach((s, o) -> to.put(s, (Integer) o));
        return to;
    }

    public <K extends Enum<K>> List<String> setEnumToList(Set<K> sets) {
        final List<String> list = new ArrayList<>();
        sets.forEach(k -> list.add(k.name()));
        return list;
    }

    public <T extends Enum<T>> Set<T> listEnumToSet(List<String> lists, Class<T> aClass) {
        final Set<T> sets = new HashSet<>();
        lists.forEach(k -> sets.add(Enum.valueOf(aClass, k)));
        return sets;
    }

    public void close() {
        this.client.close();
    }
}