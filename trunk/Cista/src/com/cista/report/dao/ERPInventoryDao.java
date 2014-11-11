package com.cista.report.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.report.to.InventoryTo;
import com.cista.system.util.CistaERPBaseDao;



public class ERPInventoryDao extends CistaERPBaseDao{

	public List<InventoryTo> getInventory() throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT INVMA.MA002 AS MA002,INVMA.MA003 AS MA003,MB001,MB002,MB003, MB004, " +
				" I.MC002 AS MC002,LC004,LC005, A.MA003 AS MA003C,C.MC002 AS MC002C,MB065,MB064,C.MC004 " +
				" FROM CISTA_HK..INVMC I " +
				" INNER JOIN CISTA_HK..INVMB AS INVMB ON MB001=I.MC001 " +
				" LEFT JOIN CISTA_HK..INVLC AS INVLC ON LC001=I.MC001 " +
				" AND LC002 ='201409' AND LC003=I.MC002 " +
				" LEFT JOIN CISTA_HK..INVLA AS INVLA ON LA001=I.MC001 AND LA009=I.MC002 " +
				" AND (LA004 BETWEEN'20140901'AND'20140930') " +
				" LEFT JOIN CISTA_HK..CMSMC C ON C.MC001=I.MC002 " +
				" LEFT JOIN CISTA_HK..INVLB AS INVLB ON I.MC001=LB001 AND LB002='201409' " +
				" LEFT JOIN CISTA_HK..INVMA AS INVMA ON MA001='1' AND MA002=MB005 " +
				" LEFT JOIN CISTA_HK..ACTMC AS ACTMC ON 1=1 " +
				" LEFT JOIN CISTA_HK..ACTMA A ON A.MA001 =INVMA.MA004  AND A.MA050=MC039 " +
				" LEFT JOIN CISTA_HK..CMSMF AS CMSMF ON MF001= 'USD' " +
				" Where 1=1  and  (MB001= N'1WS25800AK')  AND C.MC004='1' " +
				" GROUP BY INVMA.MA002,INVMA.MA003,MB001,MB002,MB003,  " +
				" MB004,MB072,I.MC002,LC004,LC005,LC030,  INVMA.MA004,A.MA003,MF004,MF005,C.MC002,MB090,  " +
				" MB065,MB064,MB046,MB047,MB050,MB051,MB097,MB053,MB054,MB055,MB056,MB069, " + 
				" MB070,MB057,MB058,MB059,MB060,MF006,LB004,LB006,LB007,LB008,LB009,  " +
				" MB100,MB101,MB102,MB052,MB098,MB103,MB104,MB105,MB106,MB107,MB108,C.MC004 " +
				" ORDER BY MA002,MB001,MC002";
		
    	ParameterizedBeanPropertyRowMapper<InventoryTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InventoryTo>();
    	rowMapper.setMappedClass(InventoryTo.class);

    	List<InventoryTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

}
