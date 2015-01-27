package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.FTYield.FTYieldIssueTo;
import com.cista.report.to.FTYield.FTYieldReceiveTo;
import com.cista.system.util.CistaERPBaseDao;



public class FTYieldDao extends CistaERPBaseDao{

	public List<FTYieldIssueTo> getFtPOIssuedQty(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = "SELECT CFIELD01 AS 'ISSUE_SHEET', MOCTE.TE011 +'-' +MOCTE.TE012 AS 'PO', " +
				" MOCTE.TE004 AS 'PART_NUM', MOCTE.TE017 AS 'PRODUCT', " +
				" MOCTE.TE018 AS 'SEPC',INVMB.MB501 AS 'GROSS_DIE', " +
				" ISNULL(MOCTE.TE005,0) AS 'ISSUE_DIE_QTY',  " +
				" MOCTE.TE006 AS 'UNIT', MOCTE.TE010 AS 'LOT' " +
				" FROM CISTA_HK..MOCTC AS MOCTC " +
				" INNER JOIN CISTA_HK..MOCTE AS MOCTE ON (MOCTC.TC001=MOCTE.TE001) AND " +
					" (MOCTC.TC002=MOCTE.TE002) AND  (MOCTE.TE005 > 0) " +
					" AND MOCTE.TE011 +'-' +MOCTE.TE012 IN ( " +
							" SELECT MOCTA.CFIELD01 AS PO_NUM " +
								" FROM CISTA_HK..MOCTA AS MOCTA " +
								" WHERE   (MOCTA.TA001 = N'5106') " +
								" AND (MOCTA.TA013 = N'Y') " +
								" AND MOCTA.TA003 <= '" + edate + "' " +
								" AND (MOCTA.TA034 LIKE '%" + product + "%') " +
   
  						") " +
  				" INNER JOIN CISTA_HK..INVMB AS INVMB ON INVMB.MB001 = MOCTE.TE004 " +
  				" WHERE (TC008='54' OR TC008='55') " +
  				" AND TC009 = 'Y' " +
  				" ORDER BY MOCTC.TC001,MOCTC.TC002 ";
		
		logger.debug("sql " + sql);
		
    	ParameterizedBeanPropertyRowMapper<FTYieldIssueTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<FTYieldIssueTo>();
    	rowMapper.setMappedClass(FTYieldIssueTo.class);

    	List<FTYieldIssueTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

	public List<FTYieldReceiveTo> getFtReceiveQty(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = "SELECT MOCTH.CFIELD01 AS 'RECEIVE_SHEET', " +
				" CONVERT(DATETIME, MOCTH.TH029) AS 'RECEIVE_DATE', " +
				" MOCTH.TH005 AS 'VENDOR', MOCTH.TH023 AS 'RECEIVE_FLAG', " +
				" MOCTI.TI013 + '-' + MOCTI.TI014 AS 'PO', MOCTI.TI010 AS 'LOT', " +
				" MOCTI.TI004 AS 'PART_NUM',MOCTI.TI005 AS 'PRODUCT',MOCTI.TI006 AS 'SPEC', " +
				" GRADE.GA, GRADE.GB, GRADE.GC, GRADE.GD, " +
				" MOCTI.TI008 AS 'UNIT'," +
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
								" WHERE   (MOCTA.TA001 = N'5106' OR MOCTA.TA001 = N'5206') " +
								" AND (MOCTA.TA013 = N'Y') " +
								" AND MOCTA.TA003 <= '" + edate + "' " +
								" AND (MOCTA.TA034 LIKE '%" + product + "%') " +
   		  					") " +
  						" WHERE (TC008='54' OR TC008='55') " +
						" AND TC009 = 'Y'" +
  				" ) " +
						
  				" INNER JOIN ( " +
  						" SELECT RECEIVE_SHEET, RECEIVE_SHEET_SEQ, LOT, PART_NUM, ISNULL(GA,0) AS GA, ISNULL(GB,0) AS GB,  " +
  								" ISNULL(GC,0) AS GC,  ISNULL(GD,0) AS GD " +
  								" FROM ( " +
  								" SELECT MG001+'-'+MG002 AS RECEIVE_SHEET, MG003 RECEIVE_SHEET_SEQ , " +
  								" MG005 AS LOT, MG006 AS PART_NUM, " +
  								" MG004 AS GRADE, ISNULL(MG008,0) AS QTY " +
  								" FROM CISTA_HK.dbo.IDLMG " +
  								" WHERE MG001+'-'+MG002 IN ( " +
  										"SELECT MOCTH.CFIELD01 AS 'RECEIVE_SHEET' " +
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
  														" WHERE   (MOCTA.TA001 = N'5106' OR MOCTA.TA001 = N'5206') " +
  														" AND (MOCTA.TA013 = N'Y') " +
  														" AND MOCTA.TA003 <= '" + edate + "' " +
  														" AND (MOCTA.TA034 LIKE '%" + product + "%') " +
  						   		  					") " +
  						  						" WHERE (TC008='54' OR TC008='55') " +
  												" AND TC009 = 'Y'" +
  						  				" ) " +
  						  				" WHERE 1=1  AND TH001= N'5906' AND TH023='Y'  AND TI037='Y' AND MOCTI.TI005 LIKE '%" + product + "%'"+ 
  								" ) " +
  								" GROUP BY MG001+'-'+MG002 ,MG003 , MG005 , MG006 , MG004 , MG008 " +
  								" ) as GroupTable " +
  								" PIVOT " +
  								" ( " +
  								" Sum(QTY) " +
  								" FOR GRADE IN ([GA], [GB], [GC], [GD]) " +
  								" ) AS PivotTable " +
  				" ) AS GRADE ON MOCTH.CFIELD01 = GRADE.RECEIVE_SHEET AND MOCTI.TI003 = GRADE.RECEIVE_SHEET_SEQ  "+
				" WHERE 1=1  AND TH001= N'5906' AND TH023='Y'  AND TI037='Y' AND MOCTI.TI005 LIKE '%" + product + "%'"+ 
				" ORDER BY 1";
	
		logger.debug("sql " + sql);
		
    	ParameterizedBeanPropertyRowMapper<FTYieldReceiveTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<FTYieldReceiveTo>();
    	rowMapper.setMappedClass(FTYieldReceiveTo.class);

    	List<FTYieldReceiveTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
}
