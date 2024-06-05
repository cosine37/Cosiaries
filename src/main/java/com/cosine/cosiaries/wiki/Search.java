package com.cosine.cosiaries.wiki;

import org.bson.Document;

import com.cosine.cosiaries.db.MongoDBConnection;

public class Search {
	static final String TITLEATTR = "title";
	static final String DBNAME = "cosiaries";
	static final String COLNAME = "pages";
	
	
	public static final int FAILURE = 400;
	public static final int SUCCESSFUL = 201;
	public static final int SAMEPAGE = 406;
	
	public static Page getPage(String title) {
		MongoDBConnection dbutil = new MongoDBConnection(DBNAME, COLNAME);
		Document doc = dbutil.read(TITLEATTR, title);
		if (doc == null) {
			return null;
		} else {
			Page page = new Page();
			page.setFromDoc(doc);
			return page;
		}
	}
	
	public static int createPage(Page p) {
		if (getPage(p.getTitle()) != null) {
			return SAMEPAGE;
		} else {
			MongoDBConnection dbutil = new MongoDBConnection(DBNAME, COLNAME);
			dbutil.insert(p.toDocument());
			return SUCCESSFUL;
		}
	}
}
