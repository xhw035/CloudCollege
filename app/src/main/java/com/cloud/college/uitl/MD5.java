package com.cloud.college.uitl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMD5String(byte[] data){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MessageDigest.getInstance(‘MD5’)错误！");
			e.printStackTrace();
		}
		md.update(data);
		return new BigInteger(1,md.digest()).toString(16);
	}
	
}
