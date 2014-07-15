package com.cista.framework.security;

/**
 * <p>Title: LicenseEncryptor</p>           
 * <p>Description: Encryptor of License </p>
 * <p>Copyright: Copyright (c) 2008</p>     
 * <p>Company: Himax</p>                   
 * @author 900730                             
 * @version 1.0                             
 */

public class EncryptionAlgorithm {
	private int _key[];
	private byte _keyBytes[];
	private int _padding;

	/**
	 * Constructor
	 * @param key key for Algorithm
	 */
	public EncryptionAlgorithm(byte key[]) {
		int klen = key.length;
		_key = new int[4];
		if (klen != 16) {
			throw new ArrayIndexOutOfBoundsException(getClass().getName()
					+ ": Key is not 16 bytes");
		}
		int i = 0;
		for (int j = 0; j < klen;) {
			_key[i] = key[j] << 24 | (key[j + 1] & 0xff) << 16
					| (key[j + 2] & 0xff) << 8 | key[j + 3] & 0xff;
			j += 4;
			i++;
		}

		_keyBytes = key;
	}

	public String toString() {
		String tea = getClass().getName();
		tea = tea + ": Tiny Encryption Algorithm (TEA)  key: "
				+ dumpBytes(_keyBytes);
		return tea;
	}

	public int[] encipher(int v[]) {
		int y = v[0];
		int z = v[1];
		int sum = 0;
		int delta = 0x9e3779b9;
		int a = _key[0];
		int b = _key[1];
		int c = _key[2];
		int d = _key[3];
		for (int n = 32; n-- > 0;) {
			sum += delta;
			y += (z << 4) + a ^ z + sum ^ (z >> 5) + b;
			z += (y << 4) + c ^ y + sum ^ (y >> 5) + d;
		}

		v[0] = y;
		v[1] = z;
		return v;
	}

	public int[] decipher(int v[]) {
		int y = v[0];
		int z = v[1];
		int sum = 0xc6ef3720;
		int delta = 0x9e3779b9;
		int a = _key[0];
		int b = _key[1];
		int c = _key[2];
		int d = _key[3];
		for (int n = 32; n-- > 0;) {
			z -= (y << 4) + c ^ y + sum ^ (y >> 5) + d;
			y -= (z << 4) + a ^ z + sum ^ (z >> 5) + b;
			sum -= delta;
		}

		v[0] = y;
		v[1] = z;
		return v;
	}

	public byte[] encipher(byte v[]) {
		byte y = v[0];
		byte z = v[1];
		int sum = 0;
		int delta = 0x9e3779b9;
		int a = _key[0];
		int b = _key[1];
		int c = _key[2];
		int d = _key[3];
		for (int n = 32; n-- > 0;) {
			sum += delta;
			y += (z << 4) + a ^ z + sum ^ (z >> 5) + b;
			z += (y << 4) + c ^ y + sum ^ (y >> 5) + d;
		}

		v[0] = y;
		v[1] = z;
		return v;
	}

	public byte[] decipher(byte v[]) {
		byte y = v[0];
		byte z = v[1];
		int sum = 0xc6ef3720;
		int delta = 0x9e3779b9;
		int a = _key[0];
		int b = _key[1];
		int c = _key[2];
		int d = _key[3];
		for (int n = 32; n-- > 0;) {
			z -= (y << 4) + c ^ y + sum ^ (y >> 5) + d;
			y -= (z << 4) + a ^ z + sum ^ (z >> 5) + b;
			sum -= delta;
		}

		v[0] = y;
		v[1] = z;
		return v;
	}

	public int[] encode(byte b[], int count) {
		int bLen = count;
		byte bp[] = b;
		_padding = bLen % 8;
		if (_padding != 0) {
			_padding = 8 - bLen % 8;
			bp = new byte[bLen + _padding];
			System.arraycopy(b, 0, bp, 0, bLen);
			bLen = bp.length;
		}
		int intCount = bLen / 4;
		int r[] = new int[2];
		int out[] = new int[intCount];
		int i = 0;
		for (int j = 0; j < bLen;) {
			r[0] = bp[j] << 24 | (bp[j + 1] & 0xff) << 16
					| (bp[j + 2] & 0xff) << 8 | bp[j + 3] & 0xff;
			r[1] = bp[j + 4] << 24 | (bp[j + 5] & 0xff) << 16
					| (bp[j + 6] & 0xff) << 8 | bp[j + 7] & 0xff;
			encipher(r);
			out[i] = r[0];
			out[i + 1] = r[1];
			j += 8;
			i += 2;
		}

		return out;
	}

	public int padding() {
		return _padding;
	}

	public byte[] decode(byte b[], int count) {
		int intCount = count / 4;
		int ini[] = new int[intCount];
		int i = 0;
		for (int j = 0; i < intCount; j += 8) {
			ini[i] = b[j] << 24 | (b[j + 1] & 0xff) << 16
					| (b[j + 2] & 0xff) << 8 | b[j + 3] & 0xff;
			ini[i + 1] = b[j + 4] << 24 | (b[j + 5] & 0xff) << 16
					| (b[j + 6] & 0xff) << 8 | b[j + 7] & 0xff;
			i += 2;
		}

		return decode(ini);
	}

	public byte[] decode(int b[]) {
		int intCount = b.length;
		byte outb[] = new byte[intCount * 4];
		int tmp[] = new int[2];
		int j = 0;
		for (int i = 0; i < intCount;) {
			tmp[0] = b[i];
			tmp[1] = b[i + 1];
			decipher(tmp);
			outb[j] = (byte) (tmp[0] >>> 24);
			outb[j + 1] = (byte) (tmp[0] >>> 16);
			outb[j + 2] = (byte) (tmp[0] >>> 8);
			outb[j + 3] = (byte) tmp[0];
			outb[j + 4] = (byte) (tmp[1] >>> 24);
			outb[j + 5] = (byte) (tmp[1] >>> 16);
			outb[j + 6] = (byte) (tmp[1] >>> 8);
			outb[j + 7] = (byte) tmp[1];
			i += 2;
			j += 8;
		}

		return outb;
	}

	private String dumpBytes(byte b[]) {
		StringBuffer r = new StringBuffer();
		String hex = "0123456789ABCDEF";
		for (int i = 0; i < b.length; i++) {
			int c = b[i] >>> 4 & 0xf;
			r.append("0123456789ABCDEF".charAt(c));
			c = b[i] & 0xf;
			r.append("0123456789ABCDEF".charAt(c));
		}

		return r.toString();
	}

}
