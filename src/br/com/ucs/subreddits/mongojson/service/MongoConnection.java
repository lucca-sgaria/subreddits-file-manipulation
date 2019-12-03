package br.com.ucs.subreddits.mongojson.service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection {
	public MongoCollection<Document> getMongoConnection() {
		MongoClient mongo = new MongoClient();
		MongoDatabase db = mongo.getDatabase("database_sample");
		return db.getCollection("subreddits");
	}

	public DBCollection getMongoConnectionDb() {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("database_sample");
		return db.getCollection("subreddits");
	}
	

}
