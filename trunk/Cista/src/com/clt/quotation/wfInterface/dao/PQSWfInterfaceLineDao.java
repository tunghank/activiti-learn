package com.clt.quotation.wfInterface.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.wfInterface.to.PQSWfInterfaceLineTo;
import com.clt.system.util.BaseDao;

public class PQSWfInterfaceLineDao extends BaseDao {
	
	public int[] batchInstPQSWfInterfaceLine(List<PQSWfInterfaceLineTo> pqsWfInterfaceLineList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into PQS_WF_INTERFACE_LINE ( " +
				" PQS_NO, SEQ_NO, ERP_QUO_ID , ERP_QUO_LINE_NO, " +
				" PARTS_NO, PARTS_DESC, UNIT, VENDOR_ID, " +
				" VENDOR_NAME, VENDOR_SITE_ID, VENDOR_SITE_CODE, " +
				" ALLOCATION, CURRENCY, PRICE_OLD, PRICE_NEW, " +
				" CP_PRICE_OLD, CP_PRICE_NEW, TAX_ID, TAX_NAME, " +
				" QUO_START_DATE_OLD, QUO_END_DATE_OLD, QUO_START_DATE_NEW, " +
				" QUO_END_DATE_NEW, PAYMENT_METHOD_OLD, PAYMENT_METHOD_NEW_ID, " +
				" PAYMENT_METHOD_NEW, CONDITIONS_TERM_OLD, CONDITIONS_TERM_NEW, " +
				" SHIP_LOCATION_ID, SHIP_LOCATION, BILL_TO_LOC_ID, " +
				" FREIGHT_TERMS_CODE, FREIGHT_TERMS, COMMENTS) " +
		" Values ( ?, ?, ?, ?, ?,?, ?, ?, ?, ?," +
		" ?, ?, ?, ?, ?,?, ?, ?, ?, ?, " +
		" ?, ?, ?, ?, ?,?, ?, ?, ?, ?, " +
		" ?, ?, ?, ? )";
			
		List<Object[]> batch = new ArrayList<Object[]>();

		for (PQSWfInterfaceLineTo interfaceLineTo : pqsWfInterfaceLineList) {
		    Object[] values = new Object[] {

		    		interfaceLineTo.getPqsNo(),
		    		interfaceLineTo.getSeqNo(),
		    		interfaceLineTo.getErpQuoId(),
		    		interfaceLineTo.getErpQuoLineNo(),
		    		interfaceLineTo.getPartsNo(),
		    		interfaceLineTo.getPartsDesc(),
		    		interfaceLineTo.getUnit(),
		    		interfaceLineTo.getVendorId(),
		    		interfaceLineTo.getVendorName(),
		    		interfaceLineTo.getVendorSiteId(),
		    		interfaceLineTo.getVendorSiteCode(),
		    		interfaceLineTo.getAllocation(),
		    		interfaceLineTo.getCurrency(),
		    		interfaceLineTo.getPriceOld(),
		    		interfaceLineTo.getPriceNew(),
		    		interfaceLineTo.getCpPriceQld(),
		    		interfaceLineTo.getCpPriceNew(),
		    		interfaceLineTo.getTaxId(),
		    		interfaceLineTo.getTaxName(),
		    		interfaceLineTo.getQuoStartDateOld(),
		    		interfaceLineTo.getQuoEndDateOld(),
		    		interfaceLineTo.getQuoStartDateNew(),
		    		interfaceLineTo.getQuoEndDateNew(),
		    		interfaceLineTo.getPaymentMethodOld(),
		    		interfaceLineTo.getPaymentMethodNewId(),
		    		interfaceLineTo.getPaymentMethodNew(),
		    		interfaceLineTo.getConditionsTermOld(),
		    		interfaceLineTo.getConditionsTermNew(),
		    		interfaceLineTo.getShipLocationId(),
		    		interfaceLineTo.getShipLocation(),
		    		interfaceLineTo.getBillToLocId(),
		    		interfaceLineTo.getFreightTermsCode(),
		    		interfaceLineTo.getFreightTerms(),
		    		interfaceLineTo.getComments()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	

}
