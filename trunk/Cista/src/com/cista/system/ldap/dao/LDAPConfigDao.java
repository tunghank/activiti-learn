package com.cista.system.ldap.dao;

/********************************************************************
 * Modify History:
 * Date Time  |  Modifier  |Description
 * 2007/09/09 |  Hank_Tang |加入可以讀取多個AD Function.   
 ********************************************************************/

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.cista.system.to.SysLdapConfigTo;
import com.cista.system.util.BaseDao;


public class LDAPConfigDao extends BaseDao {

    public List<SysLdapConfigTo> getAllConfig() {
    	SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	
    	String sql = "SELECT A.LDAP_SITE, A.LDAP_URL, A.LDAP_FACTORY, A.LDAP_CN, " +
       " A.LDAP_AUTHENTICATION, A.LDAP_USERNAME, A.LDAP_PASSWORD, A.CDT " +
       " FROM SYS_LDAP_CONFIG A " +
       " Order by A.LDAP_SITE ";
    	ParameterizedBeanPropertyRowMapper<SysLdapConfigTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysLdapConfigTo>();
    	rowMapper.setMappedClass(SysLdapConfigTo.class);

    	logger.debug(sql);
    	List<SysLdapConfigTo> result = sjt.query(sql,rowMapper);

        return result;
    }


}
