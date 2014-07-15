package com.clt.quotation.inquiry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.inquiry.to.InquiryInformTo;
import com.clt.system.util.BaseDao;

public class InquiryInformDao extends BaseDao {
	
	public int[] batchInstInquiryInform(List<InquiryInformTo> inquiryInformList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into INQUIRY_INFORM ( " +
				" INQUIRY_INFORM_UID, INQUIRY_HEADER_UID, " +
				" INQUIRY_SUPPLIER_UID, MAIL_NAME, MAIL_TO  ) " +
		" Values ( ?, ?, ?, ?, ? )";
				
		List<Object[]> batch = new ArrayList<Object[]>();
		for (InquiryInformTo informTo : inquiryInformList) {

		    Object[] values = new Object[] {
		    		informTo.getInquiryInformUid(),
		    		informTo.getInquiryHeaderUid(),
		    		informTo.getInquirySupplierUid(),
		    		informTo.getMailName(),
		    		informTo.getMailTo()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public int delInquiryInform(String inquiryHeaderUid) throws DataAccessException{
		String sql = "";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
    	sql = " DELETE FROM INQUIRY_INFORM A "
			+ " WHERE A.INQUIRY_HEADER_UID = ? " ;    	
    	logger.debug(sql);	
		int result = sjt.update(sql	, new Object[]{inquiryHeaderUid});		
		return result;		
	}
}
