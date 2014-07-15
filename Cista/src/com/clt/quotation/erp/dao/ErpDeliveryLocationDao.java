package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpDeliveryLocationTo;

import com.clt.system.util.BaseErpDao;
import com.clt.system.util.CLTUtil;

public class ErpDeliveryLocationDao extends BaseErpDao {

	private String orgSiteId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	
	public ErpDeliveryLocationTo getErpDeliveryLocation(String locationId) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  =  " SELECT LOCATION_ID, LOCATION_CODE " +
				" FROM HR_LOCATIONS_ALL_V " +
				" Where 1=1 AND ( INVENTORY_ORGANIZATION_ID ='" + orgOuId  + "' " +
				" or INVENTORY_ORGANIZATION_ID IS NULL ) " +
				" AND INACTIVE_DATE IS NULL " +
				" AND LOCATION_ID = '" + locationId + "'";
					
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpDeliveryLocationTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpDeliveryLocationTo>();
    	rowMapper.setMappedClass(ErpDeliveryLocationTo.class);

    	List<ErpDeliveryLocationTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	

}
