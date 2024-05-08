package com.cosine.cosiaries.model.login;

import org.bson.Document;
import org.bson.types.Binary;

public class User {
	String username;
	String email;
	byte[] encrypted;
	
	public User() {
		
	}
	
	public User(String username, String password, String email) {
		this.username = username;
		try {
			this.encrypted = UserUtils.encrypt(password);
		} catch (Exception e) {
			e.printStackTrace();
			this.encrypted = new byte[0];
		}
		this.email = email;
	}
	
	public void decrypt() {
		try {
			UserUtils.decrypt(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int createThis() {
		return UserUtils.createUser(this);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(byte[] encrypted) {
		this.encrypted = encrypted;
	}

	public Document toDocument() {
		Document doc = new Document();
		doc.append("username", username);
		doc.append("email", email);
		
		doc.append("encrypted", encrypted);
		return doc;
	}
	
	public void setFromDoc(Document doc) {
		username = doc.getString("username");
		email = doc.getString("email");
		Binary b = (Binary) doc.get("encrypted");
		encrypted = b.getData();
	}
	
	public static void main(String args[]){
		User user = new User("aaaa", "ashfjkdsgfdjskghksd", "111");
		System.out.println(user.createThis());
		try {
			System.out.println(UserUtils.validate("aaaa", "123"));
			System.out.println(UserUtils.validate("aaaa", "ashfjkdsgfdjskghksd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
