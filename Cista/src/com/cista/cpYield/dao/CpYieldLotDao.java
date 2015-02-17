package com.cista.cpYield.dao;


import java.util.ArrayList;
import java.util.List;

import com.cista.cpYield.to.CpYieldReportTo;
import com.cista.cpYield.to.CpYieldQueryTo;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.cista.system.util.BaseDao;


public class CpYieldLotDao extends BaseDao {
	

	private static final long serialVersionUID = 1L;

	public List<CpYieldReportTo> getBins(CpYieldQueryTo queryTo) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT * FROM ( SELECT A.CP_YIELD_UUID, A.CP_TEST_TIMES, A.CP_LOT, A.WAFER_ID, " +
					" A.MACHINE_ID, A.X_MAX_COOR, A.Y_MAX_COOR, A.FLAT, A.PASS_DIE, " +
					" A.FAIL_DIE, A.TOTEL_DIE, A.FILE_NAME, A.FILE_MIME_TYPE, " +
					" A.FTP_FLAG, A.FTP_SEND_TIME, A.CDT, B.BIN , B.PERCENTAGE " +
					" FROM CP_YIELD_LOT A , CP_YIELD_LOT_BIN B " +
					" WHERE A.CP_YIELD_UUID = B.CP_YIELD_UUID ";
			//LOT
			if( queryTo.getLot() != null && !queryTo.getLot().equals("")){
				sql = sql + " AND A.CP_LOT LIKE '%" + queryTo.getLot() + "%'";
			}
			//Ftp Flag
			if( queryTo.getFtpFlag() != null && !queryTo.getFtpFlag().equals("")){
				sql = sql + " AND A.FTP_FLAG = '" + queryTo.getFtpFlag() + "'";
			}
			//轉檔日期 Cdt
			if( queryTo.getsCdt() != null && !queryTo.getsCdt().equals("")){
				sql = sql + " AND A.CDT >= TO_DATE('" + queryTo.getsCdt() + "','YYYY-MM-DD')";
			}
			//FTP Send Time
			if( queryTo.getsFtpSendTime() != null && !queryTo.getsFtpSendTime().equals("")){
				sql = sql + " AND A.FTP_SEND_TIME >= TO_DATE('" + queryTo.getsFtpSendTime() + "','YYYY-MM-DD')";
			}
			
			sql = sql +	" ) C " +
				" PIVOT ( " +
					" MAX(PERCENTAGE) FOR BIN IN ( 0 AS BIN0, 3 AS BIN3, 5 AS BIN5, " +
					" 10 AS BIN10, 88 AS BIN88, 100 AS BIN100, 101 AS BIN101, " +
					" 121 AS BIN121, 211 AS BIN211 ) " +
					" ) " ;
		
			sql = sql + " Order by CP_LOT, CP_TEST_TIMES ";
			
		logger.debug("sql " + sql);
		
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
	
	public int[] updateCpYieldLotSendFlg(List<String> cpYieldUuidList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = " Update CP_YIELD_LOT SET FTP_FLAG = 'Y' " +
        		" WHERE CP_YIELD_UUID = ? ";
				

		List<Object[]> batch = new ArrayList<Object[]>();
        for (String cpYieldUuid : cpYieldUuidList) {
            Object[] values = new Object[] {
            		cpYieldUuid
        			};
            batch.add(values);
        }

    	logger.debug(sql);
    	
    	int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
}
