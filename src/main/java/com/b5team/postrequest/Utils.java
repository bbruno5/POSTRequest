package com.b5team.postrequest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Utils {
	
	String encode(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
		byte messageDigest[] = algorithm.digest(pwd.getBytes("UTF-8"));
		
		StringBuilder buffer = new StringBuilder();
		for(int i = 0; i < messageDigest.length; i++) {
			buffer.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return buffer.toString();
	}
}
