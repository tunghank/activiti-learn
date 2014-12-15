package com.cista.wip.dao;


import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cista.util.BaseDao;
import com.cista.wip.to.FoundryWipTo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class FoundryWipDao extends BaseDao {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public FoundryWipDao() {
		super();
	}

	public int backupFoundryWip() throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = " INSERT INTO RPT_FOUNDRY_WIP_HIS " +
        		" SELECT * " +
        		" FROM RPT_FOUNDRY_WIP ";
				
	  	logger.debug(sql);
		int result = sjt.update( sql, new Object[]{});		

    	return result;
	}

	public int deleteFoundryWip() throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = "DELETE RPT_FOUNDRY_WIP";
				
	  	logger.debug(sql);
		int result = sjt.update( sql, new Object[]{});		

    	return result;
	}
	
	public int[] insertFoundryWip(List<FoundryWipTo> foundryWipList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

        String sql = " Insert INTO RPT_FOUNDRY_WIP ( " +
        		" FOUNDRY_WIP_UUID, VENDOR_CODE, VENDOR_SITE_NUM, PROCESS, " +
        		" CISTA_PO, VENDOR_PROD, CISTA_PART_NUM, CISTA_PROJECT, " +
        		" WAFER_LOT_ID, VENDOR_LOT_ID, WAFER_QTY, LOT_TYPE, " +
        		" TOTAL_LAYER, REMAIN_LAYER, LOT_STATUS, CURR_HOLD_DAY, " +
        		" HOLD_CODE, HOLD_REAS, PRIORITY, WAFER_START, " +
        		" CURR_STAGE, STG_IN_DATE, SOD, RSOD, RPT_DATE, " +
        		" SHIP_TO ) VALUES ( " +
        		" ?,?,?,?, " +
        		" ?,?,?,?, " +
        		" ?,?,?,?, " +
        		" ?,?,?,?, " +
        		" ?,?,?,?, " +
        		" ?,?,?,?,? " +
        		" ,? ) ";
				

		List<Object[]> batch = new ArrayList<Object[]>();
        for (FoundryWipTo wipTo : foundryWipList) {
            Object[] values = new Object[] {
            		wipTo.getFoundryWipUuid(),
            		wipTo.getVendorCode(),
            		wipTo.getVendorSiteNum(),
            		wipTo.getProcess(),
            		wipTo.getCistaPo(),
            		wipTo.getVendorProd(),
            		wipTo.getCistaPartNum(),
            		wipTo.getCistaProject(),
            		wipTo.getWaferLotId(),
            		wipTo.getVendorLotId(),
            		wipTo.getWaferQty(),
            		wipTo.getLotType(),
            		wipTo.getTotalLayer(),
            		wipTo.getRemainLayer(),
            		wipTo.getLotStatus(),
            		wipTo.getCurrHoldDay(),
            		wipTo.getHoldCode(),
            		wipTo.getHoldReas(),
            		wipTo.getPriority(),
            		wipTo.getWaferStart(),
            		wipTo.getCurrStage(),
            		wipTo.getStgInDate(),
            		wipTo.getSod(),
            		wipTo.getRsod(),
            		wipTo.getRptDate(),
            		wipTo.getShipTo()
        			};
            batch.add(values);
        }

    	logger.debug(sql);
    	
    	int result [] = sjt.batchUpdate(sql, batch);
    	return result;
	}
	
}
