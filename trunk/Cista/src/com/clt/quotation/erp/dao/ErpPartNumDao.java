package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpPartNumTo;
import com.clt.system.util.BaseErpDao;
import com.clt.system.util.CLTUtil;

public class ErpPartNumDao extends BaseErpDao {
	
	private String orgId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	
	public ErpPartNumTo getPartNum(String partNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT INVENTORY_ITEM_ID , SEGMENT1 PART_NUM , " +
				" DESCRIPTION PART_NUM_DESC, PRIMARY_UOM_CODE PART_NUM_UNIT " +				
				" FROM MTL_SYSTEM_ITEMS_B MSIB " +
				" WHERE MSIB.PURCHASING_ITEM_FLAG = 'Y' " +
				" AND MSIB.SEGMENT1 = '"+ partNum + "' " +
				" AND MSIB.ORGANIZATION_ID = '" + this.orgOuId + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpPartNumTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpPartNumTo>();
    	rowMapper.setMappedClass(ErpPartNumTo.class);

    	List<ErpPartNumTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	

}
