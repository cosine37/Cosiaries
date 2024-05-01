package com.cosine.cosiaries.model.login;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bson.Document;
import org.bson.types.Binary;

import com.cosine.cosiaries.db.MongoDBConnection;

public class UserUtils {
	/**
	 * Encryption related
	 */
	static PublicKey publicKey;
	static PrivateKey privateKey;
	
	static final String publicKeyFileName = "public.key";
	static final String privateKeyFileName = "private.key";
	
	static void readPublicKey() throws Exception {
		File publicKeyFile = new File(publicKeyFileName);
		byte[] bytes = Files.readAllBytes(publicKeyFile.toPath());
		
		publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
	}
	
	static void readPrivateKey() throws Exception {
		File privateKeyFile = new File(privateKeyFileName);
		byte[] bytes = Files.readAllBytes(privateKeyFile.toPath());
		
		privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
	}
	
	public static byte[] encrypt(String password) throws Exception {
		readPublicKey();
		
	    //Creating a Cipher object
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        
	    //Initializing a Cipher object
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
	    //Adding data to the cipher
	    byte[] input = password.getBytes();	  
	    cipher.update(input);
		  
	    //encrypting the data
	    byte[] cipherText = cipher.doFinal();	 
	    return cipherText;
	}
	
	public static String decrypt(byte[] bytes) throws Exception {
		readPrivateKey();
		
		//Creating a Cipher object
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	   
	    //Initializing the same cipher for decryption
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	      
	    //Decrypting the text
	    byte[] decipheredText = cipher.doFinal(bytes);
	    String text = new String(decipheredText);
	    return text;
	}
	
	/**
	 * User related
	 */
	static final String USERNAMEATTR = "username";
	static final String DBNAME = "cosiaries";
	static final String COLNAME = "users";
	public static boolean containsUser(String username) {
		MongoDBConnection dbutil = new MongoDBConnection(DBNAME, COLNAME);
		Document doc = dbutil.read(USERNAMEATTR, username);
		if (doc == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static final int FAILURE = 400;
	public static final int SUCCESSFUL = 201;
	public static final int SAMEUSER = 406;
	
	public static int createUser(User u) {
		int result = FAILURE;
		if (containsUser(u.getUsername())) {
			result = SAMEUSER;
		} else {
			MongoDBConnection dbutil = new MongoDBConnection(DBNAME, COLNAME);
			dbutil.insert(u.toDocument());
			result = SUCCESSFUL;
		}
		return result;
	}
	
	public static boolean validate(String username, String password) throws Exception {
		boolean f = false;
		MongoDBConnection dbutil = new MongoDBConnection(DBNAME, COLNAME);
		Document doc = dbutil.read(USERNAMEATTR, username);
		if (doc == null) {
			f = false;
		} else {
			if (doc.containsKey("encrypted")) {
				Binary b = (Binary) doc.get("encrypted");
				byte[] bytes = b.getData();
				String decrypted = decrypt(bytes);
				if (decrypted.contentEquals(password)) {
					f = true;
				} else {
					f = false;
				}
			}
		}
		return f;
	}
	
}
