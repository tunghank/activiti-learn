package com.cista.system.to;

/**
 * SysLdapConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysLdapConfigTo extends com.cista.system.util.BaseObject implements
		java.io.Serializable {

	// Fields

	private String ldapSite;
	private String ldapUrl;
	private String ldapFactory;
	private String ldapCn;
	private String ldapAuthentication;
	private String ldapUsername;
	private String ldapPassword;
	private String cdt;

	// Constructors

	/** default constructor */
	public SysLdapConfigTo() {
	}

	// Property accessors

	public String getLdapSite() {
		return this.ldapSite;
	}

	public void setLdapSite(String ldapSite) {
		this.ldapSite = ldapSite;
	}

	public String getLdapUrl() {
		return this.ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public String getLdapFactory() {
		return this.ldapFactory;
	}

	public void setLdapFactory(String ldapFactory) {
		this.ldapFactory = ldapFactory;
	}

	public String getLdapCn() {
		return this.ldapCn;
	}

	public void setLdapCn(String ldapCn) {
		this.ldapCn = ldapCn;
	}

	public String getLdapAuthentication() {
		return this.ldapAuthentication;
	}

	public void setLdapAuthentication(String ldapAuthentication) {
		this.ldapAuthentication = ldapAuthentication;
	}

	public String getLdapUsername() {
		return this.ldapUsername;
	}

	public void setLdapUsername(String ldapUsername) {
		this.ldapUsername = ldapUsername;
	}

	public String getLdapPassword() {
		return this.ldapPassword;
	}

	public void setLdapPassword(String ldapPassword) {
		this.ldapPassword = ldapPassword;
	}

	public String getCdt() {
		return this.cdt;
	}

	public void setCdt(String cdt) {
		this.cdt = cdt;
	}

}