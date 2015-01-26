package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.AssemblyYieldIssueTo;
import com.cista.report.to.AssemblyYieldReceiveTo;
import com.cista.system.util.CistaERPBaseDao;



public class AssemblyYieldDao extends CistaERPBaseDao{

	public List<AssemblyYieldIssueTo> getAssemblyPOIssuedQty(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = "SELECT CFIELD01 AS 'ISSUE_SHEET', MOCTE.TE011 +'-' +MOCTE.TE012 AS 'PO', " +
				" MOCTE.TE004 AS 'PART_NUM', MOCTE.TE017 AS 'PRODUCT', " +
				" MOCTE.TE018 AS 'SEPC',INVMB.MB501 AS 'GROSS_DIE', " +
				" MOCTE.TE005 AS 'ISSUE_QTY', ISNULL(MOCTE.TE005,0)*ISNULL(INVMB.MB501,0) AS 'ISSUE_DIE_QTY', " +
				" MOCTE.TE006 AS 'UNIT', MOCTE.TE010 AS 'LOT' " +
				" FROM CISTA_HK..MOCTC AS MOCTC " +
				" INNER JOIN CISTA_HK..MOCTE AS MOCTE ON (MOCTC.TC001=MOCTE.TE001) AND " +
					" (MOCTC.TC002=MOCTE.TE002) AND  (MOCTE.TE005 > 0) " +
					" AND MOCTE.TE011 +'-' +MOCTE.TE012 IN ( " +
							" SELECT MOCTA.CFIELD01 AS PO_NUM " +
								" FROM CISTA_HK..MOCTA AS MOCTA " +
								" WHERE   (MOCTA.TA001 = N'5105') " +
								" AND (MOCTA.TA013 = N'Y') " +
								" AND MOCTA.TA003 <= '" + edate + "' " +
								" AND (MOCTA.TA034 LIKE '%" + product + "%') " +
   
  						") " +
  				" INNER JOIN CISTA_HK..INVMB AS INVMB ON INVMB.MB001 = MOCTE.TE004 " +
  				" WHERE (TC008='54' OR TC008='55') " +
  				" AND TC009 = 'Y' " +
  				" ORDER BY MOCTC.TC001,MOCTC.TC002 ";
		
		logger.debug("sql " + sql);
		
    	ParameterizedBeanPropertyRowMapper<AssemblyYieldIssueTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<AssemblyYieldIssueTo>();
    	rowMapper.setMappedClass(AssemblyYieldIssueTo.class);

    	List<AssemblyYieldIssueTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

	public List<AssemblyYieldReceiveTo> getAssemblyReceiveQty(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = "SELECT MOCTH.CFIELD01 AS 'RECEIVE_SHEET', " +
				" CONVERT(DATETIME, MOCTH.TH029) AS 'RECEIVE_DATE', " +
				" MOCTH.TH005 AS 'VENDOR', MOCTH.TH023 AS 'RECEIVE_FLAG', " +
				" MOCTI.TI013 + '-' + MOCTI.TI014 AS 'PO', MOCTI.TI010 AS 'LOT', " +
				" MOCTI.TI004 AS 'PART_NUM',MOCTI.TI005 AS 'PRODUCT',MOCTI.TI006 AS 'SPEC', " +
				" MOCTI.TI007 AS 'RECEIVE_QTY',MOCTI.TI008 AS 'UNIT',MOCTI.TI019 AS 'GOOD_QTY', " +
				" MOCTI.TI038 AS 'ITEM_FLAG' " +
				" FROM CISTA_HK..MOCTH AS MOCTH " +
				" LEFT JOIN CISTA_HK..MOCTI AS MOCTI ON TI001=TH001 AND TI002=TH002 " +
				" AND MOCTI.TI010  IN ( " +
						" SELECT  MOCTE.TE010 AS 'LOT' "+
						" FROM CISTA_HK..MOCTC AS MOCTC " +
						" INNER JOIN CISTA_HK..MOCTE AS MOCTE ON (MOCTC.TC001=MOCTE.TE001) AND " +
						" (MOCTC.TC002=MOCTE.TE002) AND  (MOCTE.TE005 > 0) " +
							" AND MOCTE.TE011 +'-' +MOCTE.TE012 IN ( " +
								" SELECT MOCTA.CFIELD01 AS PO_NUM " +
								" FROM CISTA_HK..MOCTA AS MOCTA " +
								" WHERE   (MOCTA.TA001 = N'5105') " +
								" AND (MOCTA.TA013 = N'Y') " +
								" AND MOCTA.TA003 <= '" + edate + "' " +
								" AND (MOCTA.TA034 LIKE '%" + product + "%') " +
   		  					") " +
  						" WHERE (TC008='54' OR TC008='55') " +
						" AND TC009 = 'Y'" +
  				" ) " +
  
				" WHERE 1=1  AND TH001= N'5905' AND TH023='Y'  AND TI037='Y' AND MOCTI.TI005 LIKE '%" + product + "%'"+ 
				" ORDER BY 1";
		
		logger.debug("sql " + sql);
		
    	ParameterizedBeanPropertyRowMapper<AssemblyYieldReceiveTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<AssemblyYieldReceiveTo>();
    	rowMapper.setMappedClass(AssemblyYieldReceiveTo.class);

    	List<AssemblyYieldReceiveTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
}
