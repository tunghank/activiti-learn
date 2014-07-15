package com.cista.framework.security;

import java.math.BigInteger;
/**
 * <p>Title: LicenseEncryptor</p>
 * <p>Description: Encryptor of License</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Himax</p>
 * @author 900730
 * @version 1.0
 */
public class Encryptor {
	private byte _key[];
	private EncryptionAlgorithm _althmTea;

	/**
	 * Construtor of LicenseEncryptor
	 */
	public Encryptor() {
		_key = (new BigInteger("cabe11b8d9ad59939e858f86df9b909", 16))
				.toByteArray();
		_althmTea = new EncryptionAlgorithm(_key);

	}

	public String encodeStrOf(String srcStr) {

		byte plainSource[] = srcStr.getBytes();
		int enc[] = _althmTea.encode(plainSource, plainSource.length);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < enc.length; i++) {
			String s = Integer.toHexString(enc[i]);
			for (int j = 0; j < 8 - s.length(); j++) {
				buf.append("0");
			}
			buf.append(s);
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * Method is called to decode string
	 * 
	 * @param encodeStr
	 *            string to be decoded
	 * @return decoded string
	 */
	public String decodeStrOf(String encodeStr) {
		int len = (int) ((double) encodeStr.length() / 8D + 0.48999999999999999D);
		int enc[] = new int[len];
		for (int i = 0; i < encodeStr.length(); i += 8) {
			if (i + 8 > encodeStr.length()) {
				enc[i / 8] = parseInt(encodeStr.substring(i));
			} else {
				enc[i / 8] = parseInt(encodeStr.substring(i, i + 8));
			}
		}

		byte dec[] = _althmTea.decode(enc);
		String s = new String(dec);
		int index = s.indexOf('\0');
		if (index > -1) {
			return s.substring(0, index);
		} else {
			return s;
		}
	}

	private static int parseInt(String s) {
		int result = 0;
		for (int i = 0; i < 8; i += 2) {
			if (i + 2 > s.length()) {
				result = result << 8 | Integer.parseInt(s.substring(i), 16);
			} else {
				result = result << 8
						| Integer.parseInt(s.substring(i, i + 2), 16);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Encryptor ent = new Encryptor();
		String test = "aseadmin";
		String ret = ent.encodeStrOf(test);
		// System.out.println(ret) ;
		// System.out.println(ent.decodeStrOf("65FB6975229659467D3819B7B8FC56211E105380FFF9CA1AD21182AAC7EDA9E0"));
	}

}
