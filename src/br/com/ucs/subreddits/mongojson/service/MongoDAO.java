package br.com.ucs.subreddits.mongojson.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class MongoDAO {
	
	public int insertSubreddit(String json,DBCollection collection) {
		 DBObject obj = (DBObject) JSON.parse(json);
		 collection.insert(obj);
		 return 1; 
	}

	public boolean existById(long id,DBCollection collection) {
		BasicDBObject query = new BasicDBObject();
		query.put("id",id);
		DBCursor dbObjects = collection.find(query);
		return dbObjects.count()>0;
	}
}
