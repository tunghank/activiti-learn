package com.clt.quotation.inquiry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.inquiry.to.InquirySupplierTo;

import com.clt.system.util.BaseDao;

public class InquirySupplierDao extends BaseDao {
	
	public int[] batchInstInquirySupplier(List<InquirySupplierTo> inquirySupplierList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into INQUIRY_SUPPLIER ( " +
				" INQUIRY_SUPPLIER_UID, INQUIRY_HEADER_UID, " +
				" INQUIRY_SUPPLIER_CODE, INQUIRY_SUPPLIER_NAME, " +
				" INQUIRY_SUPPLIER_SITE_CODE, INQUIRY_SUPPLIER_SITE, " +
				" INQUIRY_SUPPLIER_PART_NUM, INQUIRY_CURRENCY, " +
				" INQUIRY_PAYMENT_METHOD, INQUIRY_DELIVERY_LOCATION, " +
				" INQUIRY_SHIPPED_BY, QUOTATION_RECOVER_TIME ) " +
		" Values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		for (InquirySupplierTo supplierTo : inquirySupplierList) {
		    Object[] values = new Object[] {
		    		supplierTo.getInquirySupplierUid(),
		    		supplierTo.getInquiryHeaderUid(),
		    		supplierTo.getInquirySupplierCode(),
		    		supplierTo.getInquirySupplierName(),
		    		supplierTo.getInquirySupplierSiteCode(),
		    		supplierTo.getInquirySupplierSite(),
		    		supplierTo.getInquirySupplierPartNum(),
		    		supplierTo.getInquiryCurrency(),
		    		supplierTo.getInquiryPaymentMethod(),
		    		supplierTo.getInquiryDeliveryLocation(),
		    		supplierTo.getInquiryShippedBy(),
		    		supplierTo.getQuotationRecoverTime()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public InquirySupplierTo getInquirySupplierByKey(String inquirySupplierUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_SUPPLIER_UID, A.INQUIRY_HEADER_UID, " +
			" A.INQUIRY_SUPPLIER_CODE, A.INQUIRY_SUPPLIER_NAME, " +
			" A.INQUIRY_SUPPLIER_SITE_CODE, A.INQUIRY_SUPPLIER_SITE, " +
			" A.INQUIRY_SUPPLIER_PART_NUM, A.INQUIRY_CURRENCY, " +
			" A.INQUIRY_PAYMENT_METHOD, A.INQUIRY_DELIVERY_LOCATION, " +
			" A.INQUIRY_SHIPPED_BY, A.QUOTATION_RECOVER_TIME, A.CDT " +
			" FROM INQUIRY_SUPPLIER A" +
			" WHERE 1=1 " +
			" AND A.INQUIRY_SUPPLIER_UID ='" + inquirySupplierUid + "'" ;

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquirySupplierTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquirySupplierTo>();
    	rowMapper.setMappedClass(InquirySupplierTo.class);

    	List<InquirySupplierTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public InquirySupplierTo getInquirySupplier(String inquiryHeaderUid, String inquirySupplierCode) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_SUPPLIER_UID, A.INQUIRY_HEADER_UID, " +
			" A.INQUIRY_SUPPLIER_CODE, A.INQUIRY_SUPPLIER_NAME, " +
			" A.INQUIRY_SUPPLIER_SITE_CODE, A.INQUIRY_SUPPLIER_SITE, " +
			" A.INQUIRY_SUPPLIER_PART_NUM, A.INQUIRY_CURRENCY, " +
			" A.INQUIRY_PAYMENT_METHOD, A.INQUIRY_DELIVERY_LOCATION, " +
			" A.INQUIRY_SHIPPED_BY, A.QUOTATION_RECOVER_TIME, A.CDT " +
			" FROM INQUIRY_SUPPLIER A" +
			" WHERE 1=1 " +
			" AND A.INQUIRY_HEADER_UID ='" + inquiryHeaderUid + "'" +
			" AND A.INQUIRY_SUPPLIER_CODE ='" + inquirySupplierCode + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquirySupplierTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquirySupplierTo>();
    	rowMapper.setMappedClass(InquirySupplierTo.class);

    	List<InquirySupplierTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public List<InquirySupplierTo> getInquirySupplierByInquiryHeaderUid(String inquiryHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_SUPPLIER_UID, A.INQUIRY_HEADER_UID, " +
			" A.INQUIRY_SUPPLIER_CODE, A.INQUIRY_SUPPLIER_NAME, " +
			" A.INQUIRY_SUPPLIER_SITE_CODE, A.INQUIRY_SUPPLIER_SITE, " +
			" A.INQUIRY_SUPPLIER_PART_NUM, A.INQUIRY_CURRENCY, " +
			" A.INQUIRY_PAYMENT_METHOD, A.INQUIRY_DELIVERY_LOCATION, " +
			" A.INQUIRY_SHIPPED_BY, A.QUOTATION_RECOVER_TIME, A.CDT " +
			" FROM INQUIRY_SUPPLIER A" +
			" WHERE 1=1 " +
			" AND A.INQUIRY_HEADER_UID ='" + inquiryHeaderUid + "'" ;

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquirySupplierTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquirySupplierTo>();
    	rowMapper.setMappedClass(InquirySupplierTo.class);

    	List<InquirySupplierTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public int delInquirySupplier(String inquiryHeaderUid) throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM INQUIRY_SUPPLIER A "
			+ " WHERE A.INQUIRY_HEADER_UID = ? " ;    	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{inquiryHeaderUid});		
		return result;		
	}
}
