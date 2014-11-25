package com.cista.system.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @file : BaseObject.java
 * @author : 900730 Hank Tang
 * @Crteate Date/Time :Jul 25, 2008 1:17:48 PM
 */
public class BaseObject implements Serializable {
	protected final Log logger = LogFactory.getLog(getClass());
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode(Object o) {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	protected String showDatetime(Date pidate) {
		return getFormateDate(pidate, "yyyy-MM-dd HH:mm");
	}

	protected String showDate(Date pidate) {
		return getFormateDate(pidate, "yyyy-MM-dd");
	}

	protected String showTime(Date pidate) {
		return getFormateDate(pidate, "HH:mm");
	}

	private String getFormateDate(Date pidate, String formate) {
		if (pidate == null) {
			return null;
		} else {
			return (new SimpleDateFormat(formate)).format(pidate);
		}
	}
}
