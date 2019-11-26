package br.com.ucs.subreddits.mongojson.service;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class MongoDAO {
	
	public int insertSubreddit(String json,DBCollection collection) {
		 DBObject obj = (DBObject) JSON.parse(json);
		 collection.insert(obj);
		 return 1; 
	}
}
