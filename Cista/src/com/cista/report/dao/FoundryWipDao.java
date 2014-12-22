package com.cista.report.dao;

import java.util.List;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.FoundryWipTo;
import com.cista.system.util.BaseDao;


public class FoundryWipDao extends BaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public List<FoundryWipTo> getFountryWip(String vendorCode, String lot, String cistaProject) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		String sql  = " SELECT A.FOUNDRY_WIP_UUID, A.VENDOR_CODE, A.VENDOR, A.VENDOR_SITE_NUM, " +
				" A.PROCESS, A.CISTA_PO, A.VENDOR_PROD, A.CISTA_PART_NUM, " +
				" A.CISTA_PROJECT, A.WAFER_LOT_ID, A.VENDOR_LOT_ID, A.WAFER_QTY, " +
				" A.LOT_TYPE, A.TOTAL_LAYER, A.REMAIN_LAYER, A.LOT_STATUS, " +
				" A.CURR_HOLD_DAY, A.HOLD_CODE, A.HOLD_REAS, A.PRIORITY, " +
				" A.WAFER_START, A.CURR_STAGE, A.STG_IN_DATE, A.SOD, A.RSOD, " +
				" A.RPT_DATE, A.SHIP_TO, A.CDT " +
				" FROM RPT_FOUNDRY_WIP A" +
				" WHERE 1=1 ";
		
				if(vendorCode != null && !vendorCode.equals("") ){
					sql = sql + " AND A.VENDOR_CODE = '" + vendorCode + "'";
				}
				if(lot != null && !lot.equals("") ){
					sql = sql + " AND A.WAFER_LOT_ID Like '%" + lot + "%'";
				}
				if(cistaProject != null && !cistaProject.equals("") ){
					sql = sql + " AND A.CISTA_PROJECT Like '%" + cistaProject + "%'";
				}
				
				sql = sql + " ORDER BY CISTA_PROJECT, WAFER_LOT_ID" ;
		
				logger.debug("sql " + sql );
    	ParameterizedBeanPropertyRowMapper<FoundryWipTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<FoundryWipTo>();
    	rowMapper.setMappedClass(FoundryWipTo.class);

    	List<FoundryWipTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
