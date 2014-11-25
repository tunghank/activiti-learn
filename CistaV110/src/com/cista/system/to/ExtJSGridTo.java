package com.cista.system.to;

import java.util.Date;
import java.util.List;

/**
 * SysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ExtJSGridTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private int total;
	private List root;

	/** default constructor */
	public ExtJSGridTo() {}

	
	// Property accessors
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}


	

	

	
	
}