package com.clt.quotation.inquiry.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.inquiry.to.InquiryConfirmTo;
import com.clt.quotation.inquiry.to.InquiryHeaderTo;
import com.clt.quotation.inquiry.to.InquiryManageQueryTo;
import com.clt.system.util.BaseDao;

public class InquiryHeaderDao extends BaseDao {
	
	public int[] batchInstInquiryHeader(List<InquiryHeaderTo> inquiryHeaderList) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		String sql =  " Insert into INQUIRY_HEADER ( " +
				" INQUIRY_HEADER_UID, INQUIRY_NUM, PAPER_VER_UID, " +
				" PAPER_VER_START_DT, PAPER_VER_END_DT, CLT_INQUIRY_USER, " +
				" INQUIRY_STATUS ) " +
				" Values ( ?, ?, ?, ?, ?, ?, ? )";
				
		List<Object[]> batch = new ArrayList<Object[]>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date paperVerStartDt = new Date();
		Date paperVerEndDt = new Date();
		for (InquiryHeaderTo headerTo : inquiryHeaderList) {
			try{
				if( headerTo.getPaperVerStartDt() != null && !headerTo.getPaperVerStartDt().equals("")){
					paperVerStartDt = sdf.parse(headerTo.getPaperVerStartDt());
				}else{
					paperVerStartDt = null;
				}
				if( headerTo.getPaperVerEndDt() != null && !headerTo.getPaperVerEndDt().equals("")){
					paperVerEndDt = sdf.parse(headerTo.getPaperVerEndDt());
				}else{
					paperVerEndDt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		    Object[] values = new Object[] {
		    		headerTo.getInquiryHeaderUid(),
		    		headerTo.getInquiryNum(),
		    		headerTo.getPaperVerUid(),
		    		paperVerStartDt,
		    		paperVerEndDt,
		    		headerTo.getCltInquiryUser(),
		    		headerTo.getInquiryStatus()
		    	};
		    batch.add(values);
		}
	
		logger.debug(sql + batch.toString());
		
		int result [] = sjt.batchUpdate(sql, batch);
		return result;
	}
	
	public InquiryHeaderTo getInquiryHeaderByKey(String inquiryHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_HEADER_UID, A.INQUIRY_NUM, A.PAPER_VER_UID, " +
			" TO_CHAR(A.PAPER_VER_START_DT,'yyyy-MM-dd') PAPER_VER_START_DT , " +
			" TO_CHAR(A.PAPER_VER_END_DT,'yyyy-MM-dd') PAPER_VER_END_DT , A.CLT_INQUIRY_USER, " +
			" A.INQUIRY_STATUS, A.COMPARE_STATUS, A.CDT " +
			" FROM INQUIRY_HEADER A" +
			" WHERE 1=1 "+
			" AND A.INQUIRY_HEADER_UID ='"+ inquiryHeaderUid +"'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryHeaderTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryHeaderTo>();
    	rowMapper.setMappedClass(InquiryHeaderTo.class);

    	List<InquiryHeaderTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public InquiryHeaderTo getInquiryHeaderByInquiryNum(String inquiryNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_HEADER_UID, A.INQUIRY_NUM, A.PAPER_VER_UID, " +
			" TO_CHAR(A.PAPER_VER_START_DT,'yyyy-MM-dd') PAPER_VER_START_DT , " +
			" TO_CHAR(A.PAPER_VER_END_DT,'yyyy-MM-dd') PAPER_VER_END_DT , A.CLT_INQUIRY_USER, " +
			" A.INQUIRY_STATUS, A.COMPARE_STATUS, A.CDT " +
			" FROM INQUIRY_HEADER A" +
			" WHERE 1=1 "+
			" AND A.INQUIRY_NUM ='"+ inquiryNum +"'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryHeaderTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryHeaderTo>();
    	rowMapper.setMappedClass(InquiryHeaderTo.class);

    	List<InquiryHeaderTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public List<InquiryConfirmTo> getInquiry(InquiryManageQueryTo inquiryManageQueryTo) throws DataAccessException, ParseException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_HEADER_UID, A.INQUIRY_NUM, A.PAPER_VER_UID, " +
			" TO_CHAR(A.PAPER_VER_START_DT,'yyyy-MM-dd') PAPER_VER_START_DT, " +
			" TO_CHAR(A.PAPER_VER_END_DT,'yyyy-MM-dd') PAPER_VER_END_DT , A.CLT_INQUIRY_USER, " + 
			" A.INQUIRY_STATUS, A.COMPARE_STATUS, A.CDT INQUIRY_CDT, " +
			" B.INQUIRY_PART_NUM_UID, B.INQUIRY_HEADER_UID, B.INQUIRY_MODEL, " +
			" B.INQUIRY_MODEL_DESC, B.INQUIRY_PART_NUM, " +
			" B.INQUIRY_PART_NUM_DESC, B.INQUIRY_PART_NUM_DIFFER, " +
			" B.INQUIRY_QTY, B.INQUIRY_UNIT, " +
			" C.INQUIRY_SUPPLIER_UID, C.INQUIRY_HEADER_UID, " +
			" C.INQUIRY_SUPPLIER_CODE, C.INQUIRY_SUPPLIER_NAME, " +
			" C.INQUIRY_SUPPLIER_SITE_CODE, C.INQUIRY_SUPPLIER_SITE, " +
			" C.INQUIRY_SUPPLIER_PART_NUM, C.INQUIRY_CURRENCY, " +
			" C.INQUIRY_PAYMENT_METHOD, C.INQUIRY_DELIVERY_LOCATION, " +
			" C.INQUIRY_SHIPPED_BY, TO_CHAR(C.QUOTATION_RECOVER_TIME,'yyyy/MM/dd HH24:MI') QUOTATION_RECOVER_TIME " +
			" FROM INQUIRY_HEADER A, INQUIRY_PART_NUM B, INQUIRY_SUPPLIER C " +
			" WHERE A.INQUIRY_HEADER_UID = B.INQUIRY_HEADER_UID " +
			" AND C.INQUIRY_HEADER_UID = A.INQUIRY_HEADER_UID ";
			
			String inquiryNum = inquiryManageQueryTo.getInquiryNum();		
			String inquiryScdt = inquiryManageQueryTo.getInquiryScdt();		
			String inquiryEcdt = inquiryManageQueryTo.getInquiryEcdt();
			String recoverStime = inquiryManageQueryTo.getRecoverStime();
			String recoverEtime = inquiryManageQueryTo.getRecoverEtime();
			String inquiryPartNum = inquiryManageQueryTo.getInquiryPartNum();		
			String inquirySupplierCode = inquiryManageQueryTo.getInquirySupplierCode();
			String cltInquiryUser = inquiryManageQueryTo.getCltInquiryUser();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			if( !inquiryNum.equals("") ){
				sql = sql + " AND A.INQUIRY_NUM = '" + inquiryNum + "'";
			}
		
			if( !inquiryEcdt.equals("") ){
				Date date = sdf.parse(inquiryEcdt);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), (calendar.get(Calendar.DATE) + 1));
				inquiryEcdt = sdf.format(calendar.getTime());
			}
			
			if( !inquiryScdt.equals("") && !inquiryEcdt.equals("") ){
				sql = sql + " AND TO_DATE(SUBSTR(A.CDT,0,8),'yyyyMMdd') >= TO_DATE('" + inquiryScdt + "', 'yyyy/MM/dd')";
				sql = sql + " AND TO_DATE(SUBSTR(A.CDT,0,8),'yyyyMMdd') < TO_DATE('" + inquiryEcdt + "', 'yyyy/MM/dd')";
			}else if(!inquiryScdt.equals("")){
				sql = sql + " AND TO_DATE(SUBSTR(A.CDT,0,8),'yyyyMMdd') >= TO_DATE('" + inquiryScdt + "', 'yyyy/MM/dd')";
			}else if(!inquiryEcdt.equals("")){
				sql = sql + " AND TO_DATE(SUBSTR(A.CDT,0,8),'yyyyMMdd') < TO_DATE('" + inquiryEcdt + "', 'yyyy/MM/dd')";
			}
			
			
			if( !recoverEtime.equals("") ){
				Date date = sdf.parse(recoverEtime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), (calendar.get(Calendar.DATE) + 1));
				recoverEtime = sdf.format(calendar.getTime());
			}
			if( !recoverStime.equals("") && !recoverEtime.equals("") ){
				sql = sql + " AND C.QUOTATION_RECOVER_TIME >= TO_DATE('" + recoverStime + "', 'yyyy/MM/dd')";			
				sql = sql + " AND C.QUOTATION_RECOVER_TIME < TO_DATE('" + recoverEtime + "', 'yyyy/MM/dd')";
			}else if(!recoverStime.equals("")){
				sql = sql + " AND C.QUOTATION_RECOVER_TIME >= TO_DATE('" + recoverStime + "', 'yyyy/MM/dd')";
			}else if(!recoverEtime.equals("")){
				sql = sql + " AND C.QUOTATION_RECOVER_TIME < TO_DATE('" + recoverEtime + "', 'yyyy/MM/dd')";
			}
			
			if( !inquiryPartNum.equals("") ){
				sql = sql + " AND B.INQUIRY_PART_NUM = '" + inquiryPartNum + "'";
			}
			
			if( !inquirySupplierCode.equals("") ){
				sql = sql + " AND C.INQUIRY_SUPPLIER_CODE = '" + inquirySupplierCode + "'";
			}
			
			if( !cltInquiryUser.equals("") ){
				sql = sql + " AND A.CLT_INQUIRY_USER = '" + cltInquiryUser + "'";
			}
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryConfirmTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryConfirmTo>();
    	rowMapper.setMappedClass(InquiryConfirmTo.class);

    	List<InquiryConfirmTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}

	public List<InquiryConfirmTo> getInquiryByKey(String inquiryHeaderUid) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT A.INQUIRY_HEADER_UID, A.INQUIRY_NUM, A.PAPER_VER_UID, " +
			" TO_CHAR(A.PAPER_VER_START_DT,'yyyy-MM-dd') PAPER_VER_START_DT, " +
			" TO_CHAR(A.PAPER_VER_END_DT,'yyyy-MM-dd') PAPER_VER_END_DT , A.CLT_INQUIRY_USER, " + 
			" A.INQUIRY_STATUS, A.COMPARE_STATUS, A.CDT INQUIRY_CDT, " +
			" B.INQUIRY_PART_NUM_UID, B.INQUIRY_HEADER_UID, B.INQUIRY_MODEL, " +
			" B.INQUIRY_MODEL_DESC, B.INQUIRY_PART_NUM, " +
			" B.INQUIRY_PART_NUM_DESC, B.INQUIRY_PART_NUM_DIFFER, " +
			" B.INQUIRY_QTY, B.INQUIRY_UNIT, " +
			" C.INQUIRY_SUPPLIER_UID, C.INQUIRY_HEADER_UID, " +
			" C.INQUIRY_SUPPLIER_CODE, C.INQUIRY_SUPPLIER_NAME, " +
			" C.INQUIRY_SUPPLIER_SITE_CODE, C.INQUIRY_SUPPLIER_SITE, " +
			" C.INQUIRY_SUPPLIER_PART_NUM, C.INQUIRY_CURRENCY, " +
			" C.INQUIRY_PAYMENT_METHOD, C.INQUIRY_DELIVERY_LOCATION, " +
			" C.INQUIRY_SHIPPED_BY, C.QUOTATION_RECOVER_TIME " +
			" FROM INQUIRY_HEADER A, INQUIRY_PART_NUM B, INQUIRY_SUPPLIER C " +
			" WHERE A.INQUIRY_HEADER_UID = B.INQUIRY_HEADER_UID " +
			" AND C.INQUIRY_HEADER_UID = A.INQUIRY_HEADER_UID " +
			" AND A.INQUIRY_HEADER_UID = '" + inquiryHeaderUid + "'";
			

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<InquiryConfirmTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<InquiryConfirmTo>();
    	rowMapper.setMappedClass(InquiryConfirmTo.class);

    	List<InquiryConfirmTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public int updateInquiryStatus(String inquiryHeaderUid, String inquiryStatus) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " UPDATE INQUIRY_HEADER " + 
			"	SET INQUIRY_STATUS 	= ?  " + 
			"	WHERE  INQUIRY_HEADER_UID = ? ";
			
		logger.debug(sql);
		int result = sjt.update( sql, new Object[]{
				inquiryStatus,
				inquiryHeaderUid
		});		
		return result;		
	}
	
	public int updateInquiryCompareStatus(String inquiryHeaderUid, String compareStatus) throws DataAccessException{
		
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();		
		sql = " UPDATE INQUIRY_HEADER " + 
			"	SET COMPARE_STATUS 	= ?  " + 
			"	WHERE  INQUIRY_HEADER_UID = ? ";
			
		logger.debug(sql);
		int result = sjt.update( sql, new Object[]{
				compareStatus,
				inquiryHeaderUid
		});		
		return result;		
	}
}
