package com.cista.report.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.cista.report.to.StockHistoryTo;
import com.cista.system.util.CistaERPBaseDao;

//庫存帳

public class StockHistoryDao extends CistaERPBaseDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<StockHistoryTo> getFgOut(String edate, String product) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = "SELECT MB001, MB002,MB003,MB004, LA005, ( SUM(LA017) + SUM(LA020) ) OUT_COST "+
					" FROM ( " +
						" SELECT MB001, MB002,MB003,MB004,0 AS LA005, 0 AS LA017,0 AS LA020 " +
						" FROM CISTA_HK..INVMB AS INVMB " +
						" INNER JOIN CISTA_HK..INVMC AS INV ON INV.MC001=MB001 " +
						" LEFT JOIN  CISTA_HK..CMSMC AS CMS ON INV.MC002=CMS.MC001 " +
						" LEFT JOIN CISTA_HK..INVMA AS INVMA ON MA001='1' AND MA002=MB005 " +
						" WHERE 1=1  AND CMS.MC004='1' " +
						" and  (MB001 LIKE '6FS%') " +
						" AND MB002 LIKE '" + product + "%'" +
						" UNION ALL " +
						" SELECT MB001, MB002,MB003,MB004,LA005,ISNULL(LA017,0) LA017,ISNULL(LA020,0) LA020 " +
						" FROM CISTA_HK..INVMB AS INVMB " +
						" INNER JOIN CISTA_HK..INVLA AS INVLA ON  MB001=LA001 " +  
						" INNER JOIN CISTA_HK..CMSMC AS CMSMC ON  LA009=MC001 " + 
						" INNER JOIN CISTA_HK..CMSMQ AS CMSMQ ON  LA006=MQ001 " + 
						" LEFT JOIN CISTA_HK..INVMA AS INVMA ON MA001='1' AND MA002=MB005 " +
						" WHERE 1=1  and  (LA004 Between N'20140430' and N'" + edate + "') " +
						" AND MC004='1'  and  (MB001 LIKE '6FS%') " +
						" AND MB002 LIKE '" + product + "%'" +
						" ) A " +
						" WHERE LA005 ='-1' " +
						" GROUP BY MB001, MB002,MB003,MB004, LA005 " +
						" ORDER BY 1,2,3 ";
								
    	ParameterizedBeanPropertyRowMapper<StockHistoryTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<StockHistoryTo>();
    	rowMapper.setMappedClass(StockHistoryTo.class);

    	List<StockHistoryTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

}
