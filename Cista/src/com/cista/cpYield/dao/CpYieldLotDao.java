package com.cista.cpYield.dao;


import java.util.List;

import com.cista.cpYield.to.CpYieldReportTo;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.cista.system.util.BaseDao;

public class CpYieldLotDao extends BaseDao {
	

	private static final long serialVersionUID = 1L;

	public List<CpYieldReportTo> getBins() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT * FROM ( SELECT A.CP_YIELD_UUID, A.CP_TEST_TIMES, A.CP_LOT, A.WAFER_ID, " +
					" A.MACHINE_ID, A.X_MAX_COOR, A.Y_MAX_COOR, A.FLAT, A.PASS_DIE, " +
					" A.FAIL_DIE, A.TOTEL_DIE, A.FILE_NAME, A.FILE_MIME_TYPE, " +
					" A.FTP_FLAG, A.FTP_SEND_TIME, A.CDT, B.BIN , B.PERCENTAGE " +
					" FROM CP_YIELD_LOT A , CP_YIELD_LOT_BIN B " +
					" WHERE A.CP_YIELD_UUID = B.CP_YIELD_UUID " +
				" ) C " +
				" PIVOT ( " +
					" MAX(PERCENTAGE) FOR BIN IN ( 0 AS BIN0, 3 AS BIN3, 5 AS BIN5, " +
					" 10 AS BIN10, 88 AS BIN88, 100 AS BIN100, 101 AS BIN101, " +
					" 121 AS BIN121, 211 AS BIN211 ) " +
					" ) " ;
		
    	ParameterizedBeanPropertyRowMapper<CpYieldReportTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<CpYieldReportTo>();
    	rowMapper.setMappedClass(CpYieldReportTo.class);

    	List<CpYieldReportTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
}
