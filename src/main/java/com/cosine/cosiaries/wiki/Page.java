package com.cosine.cosiaries.wiki;

import org.bson.Document;

import com.cosine.cosiaries.model.login.User;
import com.cosine.cosiaries.model.login.UserUtils;

public class Page {
	User creater; 
	String title;
	String content;
	boolean secret;
	boolean currentVersion;
	Page previous;
	String id;
	
	
	public Page() {
		creater = null;
		title = "";
		content = "";
		secret = false;
		currentVersion = true;
		previous = null;
		id = "";
	}
	
	public Page(String title, String content, String username) {
		this();
		this.title = title;
		this.content = content;
		creater = UserUtils.getUserByName(username);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public boolean isSecret() {
		return secret;
	}
	public void setSecret(boolean secret) {
		this.secret = secret;
	}
	public boolean isCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(boolean currentVersion) {
		this.currentVersion = currentVersion;
	}
	public Page getPrevious() {
		return previous;
	}
	public void setPrevious(Page previous) {
		this.previous = previous;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Document toDocument() {
		Document doc = new Document();
		doc.append("title", title);
		doc.append("content", content);
		doc.append("secret", secret);
		doc.append("currentVersion", currentVersion);
		doc.append("creater", creater.getUsername());
		doc.append("id", id);
		return doc;
	}
	
	public void setFromDoc(Document doc) {
		title = doc.getString("title");
		content = doc.getString("content");
		secret = doc.getBoolean("secret", true);
		String username = doc.getString("creater");
		id = doc.getString("id");
		if (username == null || username.contentEquals("")) {
			creater = null;
		} else {
			creater = UserUtils.getUserByName(username);
		}
		
	}
	
}
