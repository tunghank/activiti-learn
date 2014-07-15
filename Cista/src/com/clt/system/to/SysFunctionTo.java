package com.clt.system.to;

/**
 * SysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysFunctionTo extends com.clt.system.util.BaseObject 
implements	java.io.Serializable 
{
	// Fields

	private String id;
	private String parentId;
	private String title;
	private String cls;
	private String leaf;
	private String url;
	private String hrefTarget;
	private String cdt;
	
	// for function search
	private String parentName;

	// Constructors

	/** default constructor */
	public SysFunctionTo() {
	}



	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCls() {
		return this.cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getLeaf() {
		return this.leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHrefTarget() {
		return this.hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

}