package com.cista.system.util;

import java.util.UUID;

import com.cista.system.util.CistaUtil;
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println ( CistaUtil.encodePasswd("222222") );
		//System.out.println ( CistaUtil.decodePasswd("6DA903E2099DCE0E") );
		UUID uud = UUID.randomUUID();
		
		System.out.println ( uud.toString().toUpperCase());
	}

}
