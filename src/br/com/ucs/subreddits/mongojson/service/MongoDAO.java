package br.com.ucs.subreddits.mongojson.service;

import br.com.ucs.subreddits.manipulation.util.AES;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.operation.OrderBy;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.util.*;

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

    public void findByDate(int day,int month,int year,DBCollection collection) {
	    String date = year + "-"+month+"-"+day;
        BasicDBObject query = new BasicDBObject();
        query.put("date",date);
        DBCursor dbObjects = collection.find(query);

        while (dbObjects.hasNext()) {
            System.out.println(dbObjects.next().toString());
        }
    }

    public void findByName(String name,DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("displayName",name);
        DBCursor dbObjects = collection.find(query);

        DBObject next = dbObjects.next();
        System.out.println(next.toString());
        String displayName = (String) next.get("displayName");

        AES aes = new AES();
        String decrypt = aes.decrypt(displayName, "PERNAMBUCO!");
        System.out.println(decrypt);
    }

    public void findID(long id,DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("id",id);
        DBCursor dbObjects = collection.find(query);

        while (dbObjects.hasNext()) {
            System.out.println(dbObjects.next().toString());
        }
    }

    public void monthMost(DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        DBCursor dbObjects = collection.find(query);

        List<Date> dates = new ArrayList<>();
        while (dbObjects.hasNext()) {
            DBObject next = dbObjects.next();
            long timestamp = (Long) next.get("createdUtc");
            Date date = new Date(timestamp);
            dates.add(date);
        }

        Map<String,Long> map = new HashMap<>();
        for (Date date : dates) {

        }
    }

    public void mostSubscriber(DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        DBCursor cursor = collection.find(query)
                .sort(new BasicDBObject("subscribers", OrderBy.DESC.getIntRepresentation()))
                .limit(5);

        while (cursor.hasNext()) {
            System.out.println(cursor.next().toString());
        }

    }

    public void findIDReddit(String id,DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("idReddit",id);
        DBCursor dbObjects = collection.find(query);

        while (dbObjects.hasNext()) {
            System.out.println(dbObjects.next().toString());
        }
    }

    public void createIndex(MongoCollection<Document> collection) {
        BasicDBObject query = new BasicDBObject();
	    collection.createIndex(Indexes.ascending("idReddit"));
	    collection.createIndex(Indexes.ascending("displayName"));
    }
}
