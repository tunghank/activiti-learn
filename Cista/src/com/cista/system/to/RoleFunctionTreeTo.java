/**
 * 
 */
package com.cista.system.to;

import java.io.Serializable;
import java.util.List;

import com.cista.system.util.BaseObject;

/**
 * @author 900730
 *
 */
public class RoleFunctionTreeTo extends BaseObject implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String text;
    private boolean leaf;
    private String cls;
    private String href;
    private String hrefTarget;
    private List<RoleFunctionTreeTo> children;
    
    private boolean checked;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * @return the cls
	 */
	public String getCls() {
		return cls;
	}
	/**
	 * @param cls the cls to set
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}
	/**
	 * @return the children
	 */

	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<RoleFunctionTreeTo> getChildren() {
		return children;
	}
	public void setChildren(List<RoleFunctionTreeTo> children) {
		this.children = children;
	}
	  
    
}
