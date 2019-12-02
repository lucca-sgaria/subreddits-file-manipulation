package br.com.ucs.subreddits.mongojson.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.com.ucs.subreddits.manipulation.model.Subreddit;

public class JsonGenerator {
	public List<String> getJson(List<Subreddit> list) {
		Gson gson = new Gson();
		List<String> results = new ArrayList<>();

		list.stream().map(sub -> gson.toJson(sub)).forEach(str -> results.add(str));

		return results;
	}

	public String getJson(Subreddit sub) {
		Gson gson = new Gson();

		return gson.toJson(sub);
	}
	
}
