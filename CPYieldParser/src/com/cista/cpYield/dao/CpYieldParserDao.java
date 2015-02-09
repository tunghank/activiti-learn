package com.cista.cpYield.dao;


import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cista.util.BaseDao;
import com.cista.cpYield.to.CpYieldLotBinTo;
import com.cista.cpYield.to.CpYieldLotTo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class CpYieldParserDao extends BaseDao {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public CpYieldParserDao() {
		super();
	}

	public int[] insertCpYieldLot(CpYieldLotTo cpYieldLotTo) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = " Insert INTO CP_YIELD_LOT ( " +
        		" CP_YIELD_UUID, CP_TEST_TIMES, CP_LOT, WAFER_ID, " +
        		" MACHINE_ID, X_MAX_COOR, Y_MAX_COOR, FLAT, PASS_DIE, " +
        		" FAIL_DIE, TOTEL_DIE, FILE_NAME, FILE_MIME_TYPE, " +
        		" FTP_FLAG ) VALUES ( " +
        		" ?,?,?,?, " +
        		" ?,?,?,?,?, " +
        		" ?,?,?,?, " +
        		" ? ) ";
				

		List<Object[]> batch = new ArrayList<Object[]>();
        //for (FoundryWipTo wipTo : foundryWipList) {
            Object[] values = new Object[] {
            		cpYieldLotTo.getCpYieldUuid(),
            		cpYieldLotTo.getCpTestTimes(),
            		cpYieldLotTo.getCpLot(),
            		cpYieldLotTo.getWaferId(),
            		cpYieldLotTo.getMachineId(),
            		cpYieldLotTo.getxMaxCoor(),
            		cpYieldLotTo.getyMaxCoor(),
            		cpYieldLotTo.getFlat(),
            		cpYieldLotTo.getPassDie(),
            		cpYieldLotTo.getFailDie(),
            		cpYieldLotTo.getTotelDie(),
            		cpYieldLotTo.getFileName(),
            		cpYieldLotTo.getFileMimeType(),
            		cpYieldLotTo.getFtpFlag()
        			};
            batch.add(values);
        //}

    	logger.debug(sql);
    	
    	int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
	
	public int[] insertCpYieldLotBin(List<CpYieldLotBinTo> cpYieldLotBins) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = " Insert INTO CP_YIELD_LOT_BIN ( " +
        		" CP_YIELD_BIN_UUID, CP_YIELD_UUID, BIN, DIE, PERCENTAGE ) VALUES ( " +
        		" ?,?,?,?,? ) ";
				

		List<Object[]> batch = new ArrayList<Object[]>();
        for (CpYieldLotBinTo binTo : cpYieldLotBins) {
            Object[] values = new Object[] {
            		binTo.getCpYieldBinUuid(),
            		binTo.getCpYieldUuid(),
            		binTo.getBin(),
            		binTo.getDie(),
            		binTo.getPercentage()
        			};
            batch.add(values);
        }

    	logger.debug(sql);
    	
    	int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
}
