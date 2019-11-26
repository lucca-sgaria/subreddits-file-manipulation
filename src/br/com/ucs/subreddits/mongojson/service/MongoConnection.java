package br.com.ucs.subreddits.mongojson.service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoConnection {
	public DBCollection getMongoConnection() {
		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("database_sample");
		return db.getCollection("subreddits");
	}
	

}
