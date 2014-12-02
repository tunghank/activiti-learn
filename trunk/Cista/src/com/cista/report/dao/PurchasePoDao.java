package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.PurchasePoTo;
import com.cista.system.util.CistaERPBaseDao;



public class PurchasePoDao extends CistaERPBaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PurchasePoTo getOpenPoQtyProduct(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT SUBSTRING(TD006,0,6) PRODUCT_SPEC, " +
					" SUM(PURTD.TD008) AS PURCHASE_QTY, " +
					" SUM(PURTD.TD015) AS RECEIVE_QTY, " +
					" SUM(PURTD.TD008)- SUM(PURTD.TD015) AS NOT_RECEIVE_QTY " +
					" FROM CISTA_HK..PURTC AS PURTC " +
					" LEFT JOIN CISTA_HK..PURTD AS PURTD ON TD001=TC001 AND TD002=TC002 " +
					" LEFT JOIN CISTA_HK..PURMA AS PURMA ON PURMA.MA001=TC004 " +
					" LEFT JOIN CISTA_HK..CMSMB AS CMSMB ON MB001=TC010 " +
					" LEFT JOIN CISTA_HK..CMSMV AS CMSMV ON MV001=TC011 " +
					" LEFT JOIN CISTA_HK..CMSMC AS CMSMC ON MC001=TD007 " +
					" LEFT JOIN CISTA_HK..CMSNA AS CMSNA ON NA002=TC027 AND NA001='1' " +
					" LEFT JOIN CISTA_HK..PURMA AS P     ON P.MA001=TD017 " +
					" LEFT JOIN CISTA_HK..CMSNK AS CMSNK ON PURTC.TC048=CMSNK.NK001 " +
					" LEFT JOIN CISTA_HK..CMSNB AS CMSNB ON PURTD.TD022=CMSNB.NB001 " +
					" LEFT JOIN CISTA_HK..CMSMF AS C ON C.MF001= 'USD' " +
					" Where (  TC014='Y') " +
					" AND TC001 = '3301' " +
					" AND SUBSTRING(TD006,0,6) ='" + product + "' " +
					" Group By SUBSTRING(TD006,0,6) " +
					" Having (SUM(PURTD.TD008)- SUM(PURTD.TD015)) > 0 " +
					" ORDER BY 1,2,3,4";
		
		logger.debug("sql " + sql);
		
    	ParameterizedBeanPropertyRowMapper<PurchasePoTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<PurchasePoTo>();
    	rowMapper.setMappedClass(PurchasePoTo.class);

    	List<PurchasePoTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}

	public List<PurchasePoTo> getOpenPO3301(String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT TC004 VENDOR ,PURMA.MA002 VENDOR_NAME, " +
					"	TD004 PARTNUM,TD005 PRODUCT,TD006 PRODUCT_SPEC, " +
					" SUM(PURTD.TD008) AS PURCHASE_QTY, SUM(PURTD.TD015) AS RECEIVE_QTY, " +
					" SUM(PURTD.TD008)- SUM(PURTD.TD015) AS NOT_RECEIVE_QTY, " +
					" TD009 UNIT,TD010 UNIT_PRICE,SUM(TD011) PO_AMOUNT " +
					" FROM CISTA_HK..PURTC AS PURTC " +
					" LEFT JOIN CISTA_HK..PURTD AS PURTD ON TD001=TC001 AND TD002=TC002 " +
					" LEFT JOIN CISTA_HK..PURMA AS PURMA ON PURMA.MA001=TC004 " +
					" LEFT JOIN CISTA_HK..CMSMB AS CMSMB ON MB001=TC010 " +
					" LEFT JOIN CISTA_HK..CMSMV AS CMSMV ON MV001=TC011 " +
					" LEFT JOIN CISTA_HK..CMSMC AS CMSMC ON MC001=TD007 " +
					" LEFT JOIN CISTA_HK..CMSNA AS CMSNA ON NA002=TC027 AND NA001='1' " +
					" LEFT JOIN CISTA_HK..PURMA AS P     ON P.MA001=TD017 " +
					" LEFT JOIN CISTA_HK..CMSNK AS CMSNK ON PURTC.TC048=CMSNK.NK001 " +
					" LEFT JOIN CISTA_HK..CMSNB AS CMSNB ON PURTD.TD022=CMSNB.NB001 " +
					" LEFT JOIN CISTA_HK..CMSMF AS C ON C.MF001= 'USD' " +
					" Where (  TC014='Y') " +
					" AND TC001 = '3301' " +
					" AND SUBSTRING(TD006,0,6) ='" + product + "' " +
					" Group By SUBSTRING(TD006,0,6) " +
					" Having (SUM(PURTD.TD008)- SUM(PURTD.TD015)) > 0 " +
					" ORDER BY 1,2,3,4";
		
		logger.debug("sql " + sql);
		
    	ParameterizedBeanPropertyRowMapper<PurchasePoTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<PurchasePoTo>();
    	rowMapper.setMappedClass(PurchasePoTo.class);

    	List<PurchasePoTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
}
