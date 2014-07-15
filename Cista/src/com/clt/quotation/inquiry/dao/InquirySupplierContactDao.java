package com.clt.quotation.inquiry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.inquiry.to.InquirySupplierContactTo;

import com.clt.system.util.BaseDao;

public class InquirySupplierContactDao extends BaseDao {
	
	public int[] batchInstInquirySupplierContact(List<InquirySupplierContactTo> inquirySupplierContactList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into INQUIRY_SUPPLIER_CONTACT ( " +
				" INQUIRY_SUPPLIER_CONTACT_UID, INQUIRY_HEADER_UID, INQUIRY_SUPPLIER_UID, " +
				" INQUIRY_SUPPLIER_CODE, INQUIRY_SUPPLIER_CONTACT, " +
				" INQUIRY_SUPPLIER_EMAIL, INQUIRY_SUPPLIER_PHONE ) " +
				" Values ( ?, ?, ?, ?, ?, ?, ? )";
		
		List<Object[]> batch = new ArrayList<Object[]>();
		for (InquirySupplierContactTo supplierContactTo : inquirySupplierContactList) {
		    Object[] values = new Object[] {
		    		supplierContactTo.getInquirySupplierContactUid(),
		    		supplierContactTo.getInquiryHeaderUid(),
		    		supplierContactTo.getInquirySupplierUid(),
		    		supplierContactTo.getInquirySupplierCode(),
		    		supplierContactTo.getInquirySupplierContact(),
		    		supplierContactTo.getInquirySupplierEmail(),
		    		supplierContactTo.getInquirySupplierPhone()

		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public List<InquirySupplierContactTo> getSupplierContactBySupplierUid(String inquirySupplierUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_SUPPLIER_CONTACT_UID, A.INQUIRY_HEADER_UID, " +
	   " A.INQUIRY_SUPPLIER_UID, " +
       " A.INQUIRY_SUPPLIER_CODE, A.INQUIRY_SUPPLIER_CONTACT, " +
       " A.INQUIRY_SUPPLIER_EMAIL, A.INQUIRY_SUPPLIER_PHONE, A.CDT " +
       " FROM INQUIRY_SUPPLIER_CONTACT A " +
       " WHERE A.INQUIRY_SUPPLIER_UID ='"+ inquirySupplierUid + "'" ;

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquirySupplierContactTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquirySupplierContactTo>();
    	rowMapper.setMappedClass(InquirySupplierContactTo.class);

    	List<InquirySupplierContactTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public InquirySupplierContactTo getSupplierContactBySupplierUid(String inquirySupplierUid, String inquiryHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_SUPPLIER_CONTACT_UID, A.INQUIRY_HEADER_UID, " +
	   " A.INQUIRY_SUPPLIER_UID, " +
       " A.INQUIRY_SUPPLIER_CODE, A.INQUIRY_SUPPLIER_CONTACT, " +
       " A.INQUIRY_SUPPLIER_EMAIL, A.INQUIRY_SUPPLIER_PHONE, A.CDT " +
       " FROM INQUIRY_SUPPLIER_CONTACT A " +
       " WHERE A.INQUIRY_SUPPLIER_UID ='"+ inquirySupplierUid + "'" +
       " AND A.INQUIRY_HEADER_UID = '" + inquiryHeaderUid + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquirySupplierContactTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquirySupplierContactTo>();
    	rowMapper.setMappedClass(InquirySupplierContactTo.class);

    	List<InquirySupplierContactTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public int delSupplierContact(String inquiryHeaderUid) throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM INQUIRY_SUPPLIER_CONTACT A "
			+ " WHERE A.INQUIRY_HEADER_UID = ? " ;    	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{inquiryHeaderUid});		
		return result;		
	}
}
