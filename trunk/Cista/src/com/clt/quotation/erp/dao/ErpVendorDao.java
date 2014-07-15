package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.PoVendorSitesAllTo;
import com.clt.quotation.erp.to.ErpVendorTo;
import com.clt.system.util.CLTUtil;
import com.clt.system.util.BaseErpDao;

public class ErpVendorDao extends BaseErpDao {
	
	private String orgSiteId = CLTUtil.getMessage("System.ERP.Org");
	private String orgOuId = CLTUtil.getMessage("System.ERP.OU");
	
	public PoVendorSitesAllTo getPoVendorSites(String vendorSiteId, String vendorId) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT VENDOR_SITE_ID, VENDOR_ID, VENDOR_SITE_CODE, " +
				" ADDRESS_LINE1 ADDRESS_LINE_ONE, ADDRESS_LINE2 ADDRESS_LINE_TWO, " +
				" ADDRESS_LINE2 ADDRESS_LINE_THREE , CITY, COUNTRY, " +
				" SHIP_TO_LOCATION_ID, BILL_TO_LOCATION_ID, FOB_LOOKUP_CODE, " +
				" PAYMENT_METHOD_LOOKUP_CODE, TERMS_DATE_BASIS, VAT_CODE, " +
				" PAYMENT_PRIORITY, TERMS_ID, PAY_DATE_BASIS_LOOKUP_CODE, " +
				" INVOICE_CURRENCY_CODE, PAYMENT_CURRENCY_CODE, ORG_ID " +
				" FROM PO_VENDOR_SITES_ALL SITE " +
				" WHERE 1=1" +
				" AND ORG_ID = '" + this.orgSiteId + "'" +
				" AND VENDOR_SITE_ID ='" + vendorSiteId + "'" +
				" AND VENDOR_ID ='" + vendorId + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<PoVendorSitesAllTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<PoVendorSitesAllTo>();
    	rowMapper.setMappedClass(PoVendorSitesAllTo.class);

    	List<PoVendorSitesAllTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public ErpVendorTo getErpVendor(String vendorId) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT CONT.CONTACT CONTACT, CONT.EMAIL CONTACT_EMAIL, " +
				" VENDOR.VENDOR_ID SUPPLIER_CODE, VENDOR.VENDOR_NAME SUPPLIER_NAME, " +
				" VENDOR.PAYMENT_CURRENCY_CODE PAYMENT_CURRENCY, " + 
				" VENDOR.PAY_DATE_BASIS_LOOKUP_CODE PAYMENT_METHOD, " +
				" SITE.VENDOR_SITE_ID SUPPLIER_SITE_CODE, SITE.VENDOR_SITE_CODE SUPPLIER_SITE " + 
				" FROM PO_SUPPLIER_CONTACTS_VAL_V CONT, PO_VENDORS VENDOR, PO_VENDOR_SITES_ALL SITE " +
				" WHERE 1=1" +
				" AND VENDOR.VENDOR_ID = SITE.VENDOR_ID " +
				" AND SITE.VENDOR_SITE_ID = CONT.VENDOR_SITE_ID " +
				" AND SITE.ORG_ID ='"+ this.orgSiteId  + "' " +
				" AND VENDOR.ENABLED_FLAG ='Y' " +
				" AND SITE.INACTIVE_DATE IS NULL " +
				" AND VENDOR.VENDOR_ID = '" + vendorId + "'" + 
				" Order By VENDOR.VENDOR_ID ";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<ErpVendorTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpVendorTo>();
    	rowMapper.setMappedClass(ErpVendorTo.class);

    	List<ErpVendorTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
}
