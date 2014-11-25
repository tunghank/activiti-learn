package com.cista.system.to;

import java.util.HashSet;
import java.util.Set;

/**
 * SysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysFunParameterTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {

	// Fields
	private String funName;
	private String funFieldName;
	private String fieldValue;
	private String item;
	private String fieldShowName;
	private String cdt;

	// Constructors
	/** default constructor */
	public SysFunParameterTo() {}

	// Property accessors
	
	/**
	 * @return the funName
	 */
	public String getFunName() {
		return funName;
	}

	/**
	 * @param funName the funName to set
	 */
	public void setFunName(String funName) {
		this.funName = funName;
	}

	/**
	 * @return the funFieldName
	 */
	public String getFunFieldName() {
		return funFieldName;
	}

	/**
	 * @param funFieldName the funFieldName to set
	 */
	public void setFunFieldName(String funFieldName) {
		this.funFieldName = funFieldName;
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the fieldShowName
	 */
	public String getFieldShowName() {
		return fieldShowName;
	}

	/**
	 * @param fieldShowName the fieldShowName to set
	 */
	public void setFieldShowName(String fieldShowName) {
		this.fieldShowName = fieldShowName;
	}

	/**
	 * @return the cdt
	 */
	public String getCdt() {
		return cdt;
	}

	/**
	 * @param cdt the cdt to set
	 */
	public void setCdt(String cdt) {
		this.cdt = cdt;
	}
	
}