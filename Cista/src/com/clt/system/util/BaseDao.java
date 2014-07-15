package com.clt.system.util;

import java.io.Serializable;
import com.clt.system.util.CLTUtil;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * @file : BaseService.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 25, 2008 2:07:12 PM
 */
public class BaseDao extends JdbcDaoSupport implements Serializable {
	private TransactionTemplate transactionTemplate;
	private SimpleJdbcTemplate simpleJdbcTemplate;


	/**
	 * Default constructor.
	 * 
	 */
	public BaseDao() {
		super();
        this.setDataSource(CLTUtil.getDataSource());
        this.setTransactionTemplate(CLTUtil.getTransactionTemplate());
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(this.getDataSource());
        this.setSimpleJdbcTemplate(this.simpleJdbcTemplate);
    }
	
	/**
	 * Return default TransactionTemplate for transaction management.
	 * 
	 * @return TransactionTemplate
	 */
	protected TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	/**
	 * Return default TransactionTemplate for transaction management.
	 * @param tt TransactionTemplate
	 */
	protected void setTransactionTemplate(final TransactionTemplate tt) {
		transactionTemplate = tt;
	}
	
	/**
	 * @return the simpleJdbcTemplate
	 */
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	/**
	 * @param simpleJdbcTemplate the simpleJdbcTemplate to set
	 */
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
	
	/**
	 * build judge empty SQL.
	 * @param value columnName string
	 * @return String
	 */
	
	protected String getAssertEmptyString(final String columnName) {
		return columnName != null && !columnName.equals("") ? columnName
				+ " is not null and length(trim(" + columnName
				+ ")) is not null " : " 1=1 ";
	}
	
	/**
	 * return right string for SQL.
	 * @param value A string.
	 * @return null or a string quoted by single quote.
	 */
	protected String getLikeSQLString(final String value) {
		if (value == null) {
			return "NULL";
		} else {
			return "'" + toDBString(value) + "%'";
		}
	}

	/**
	 * This method is used to replace the "'" string to "''".
	 * @param str the String to format
	 * @return The proper String.
	 */
	private String toDBString(final String str) {
		if (str == null) {
			return "";
		}
		StringBuffer dbStr = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '\'') {
				dbStr.append("''");
			} else {
				dbStr.append(c);
			}
		}
		return dbStr.toString().trim();
	}

    /**
     * Execute operation in transaction.
     * @param callback Something need to do in transaction.
     */
    public void doInTransaction(final TransactionCallbackWithoutResult callback) {
        transactionTemplate.execute(callback);
    }
    
	/**
	 * return right string for SQL.
	 * @param value A string.
	 * @return null or a string quoted by single quote.
	 */
	protected String getLikeSQL(final String value) {
		if (value == null) {
			return "NULL";
		} else {
			return "'%" + toDBString(value) + "%'";
		}
	}
}

