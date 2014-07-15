package com.clt.quotation.wfInterface.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.wfInterface.to.PQSWfInterfaceHTo;
import com.clt.system.util.BaseDao;

public class PQSWfInterfaceHDao extends BaseDao {
	
	public int[] instPQSWfInterfaceH(PQSWfInterfaceHTo pqsWfInterfaceHTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into PQS_WF_INTERFACE_H ( " +
				" PQS_NO, ORG_SITE_ID, ORG_OU_ID, FILL_IN_USER_ID ) " +
		" Values ( ?, ?, ?, ? )";
				
		List<Object[]> batch = new ArrayList<Object[]>();


		    Object[] values = new Object[] {
		    		pqsWfInterfaceHTo.getPqsNo(),
		    		pqsWfInterfaceHTo.getOrgSiteId(),
		    		pqsWfInterfaceHTo.getOrgOuId(),
		    		pqsWfInterfaceHTo.getFillInUserId()
		    	};
		    
		    batch.add(values);
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	

}
