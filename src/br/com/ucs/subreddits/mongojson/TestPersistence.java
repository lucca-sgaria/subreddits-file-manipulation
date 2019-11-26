package br.com.ucs.subreddits.mongojson;


import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class TestPersistence {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        DB database = mongoClient.getDB("TheDatabaseName");
        DBCollection collection = database.getCollection("TheCollectionName");
        

    }
}
