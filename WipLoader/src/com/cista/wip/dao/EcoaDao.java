package com.cista.wip.dao;


import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cista.util.BaseDao;
import com.cista.wip.to.COFTapeTo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class EcoaDao extends BaseDao {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public EcoaDao() {
		super();
	}



	public int[] insertCOFTape(List<COFTapeTo> cofTapeList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = " Insert Into T_ECOA_COF_TAPE " +
        "( TAPE_ID, PRODUCT_ID, TAPE_LOT, OLB_UPTOL, OLB_LOWTOL, " +
        " OLB_USL, OLB_CSL, OLB_LSL, OLB_AVG, OLB_MAX, OLB_MIN, " +
        " OLB_DATA1, OLB_DATA2, OLB_DATA3, OLB_DATA4, SR_UPTOL, " +
        " SR_LOWTOL, SR_USL, SR_CSL, SR_LSL, SR_AVG, SR_MAX, " +
        " SR_MIN, SR_DATA1, SR_DATA2, SR_DATA3, SR_DATA4, FILE_NAME ) "+ 
        " Values ( ?,?,?,?,?,?,?,?,?,?," +
        		  "?,?,?,?,?,?,?,?,?,?," +
        		  "?,?,?,?,?,?,?,? )";
				
	  	logger.debug(sql);
		List<Object[]> batch = new ArrayList<Object[]>();
        for (COFTapeTo tapeTo : cofTapeList) {
            Object[] values = new Object[] {
            		tapeTo.getTapeId(),
            		tapeTo.getProductId(),
            		tapeTo.getTapeLot(),
            		tapeTo.getOlbUptol(),
            		tapeTo.getOlbLowtol(),
            		tapeTo.getOlbUsl(),
            		tapeTo.getOlbCsl(),
            		tapeTo.getOlbLsl(),
            		tapeTo.getOlbAvg(),
            		tapeTo.getOlbMax(),
            		tapeTo.getOlbMin(),
            		tapeTo.getOlbData1(),
            		tapeTo.getOlbData2(),
            		tapeTo.getOlbData3(),
            		tapeTo.getOlbData4(),
            		tapeTo.getSrUptol(),
            		tapeTo.getSrLowtol(),
            		tapeTo.getSrUsl(),
            		tapeTo.getSrCsl(),
            		tapeTo.getSrLsl(),
            		tapeTo.getSrAvg(),
            		tapeTo.getSrMax(),
            		tapeTo.getSrMin(),
            		tapeTo.getSrData1(),
            		tapeTo.getSrData2(),
            		tapeTo.getSrData3(),
            		tapeTo.getSrData4(),
            		tapeTo.getFileName()
        			};
            batch.add(values);
        }

    	logger.debug(sql);
    	
    	int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
	
}
