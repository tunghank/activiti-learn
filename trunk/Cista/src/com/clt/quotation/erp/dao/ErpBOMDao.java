package com.clt.quotation.erp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.quotation.erp.to.ErpBOMTo;
import com.clt.quotation.erp.to.PartNumElementTo;
import com.clt.system.util.BaseErpDao;

public class ErpBOMDao extends BaseErpDao {
	
	public PartNumElementTo getPartNumElement(String partNum, String elementName) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT SEGMENT1 PART_NUM, DESCRIPTION PART_NUM_DESC, " +
				" PRIMARY_UOM_CODE,ELEMENT_VALUE,ELEMENT_NAME " +
				
				" FROM MTL_DESCR_ELEMENT_VALUES_V V, MTL_SYSTEM_ITEMS_B MSIB " +
				" WHERE PURCHASING_ITEM_FLAG = 'Y' " +
				" AND MSIB.INVENTORY_ITEM_ID = V.INVENTORY_ITEM_ID " +
				" AND MSIB.SEGMENT1 = '"+ partNum + "' " +
				"AND ELEMENT_NAME = '"+ elementName + "'";

		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<PartNumElementTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<PartNumElementTo>();
    	rowMapper.setMappedClass(PartNumElementTo.class);

    	List<PartNumElementTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}	
	}
	
	public List<PartNumElementTo> getAltermateByMain(String mainPartNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  =  " SELECT DISTINCT SEGMENT1 PART_NUM, DESCRIPTION PART_NUM_DESC " +
		  		" FROM MTL_SYSTEM_ITEMS_B MSI2 " +
		  		" WHERE  MSI2.INVENTORY_ITEM_ID IN ( " +
		  			" SELECT DISTINCT BSC.SUBSTITUTE_COMPONENT_ID " +
		  			" FROM  MTL_SYSTEM_ITEMS_B MSI1, BOM_INVENTORY_COMPONENTS  BIC, " +
		  			" BOM_SUBSTITUTE_COMPONENTS BSC " +
		  			" WHERE  MSI1.SEGMENT1 = '"+ mainPartNum + "' " +
		  			" AND MSI1.INVENTORY_ITEM_ID = BIC.COMPONENT_ITEM_ID " +
		  			" AND BIC.COMPONENT_SEQUENCE_ID = BSC.COMPONENT_SEQUENCE_ID(+) " +
		  			" )";
					
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<PartNumElementTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<PartNumElementTo>();
    	rowMapper.setMappedClass(PartNumElementTo.class);

    	List<PartNumElementTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public List<PartNumElementTo> getMainByAltermate(String altermatePartNum) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT MSI1.SEGMENT1 PART_NUM, MSI1.DESCRIPTION PART_NUM_DESC " +  
			" FROM  MTL_SYSTEM_ITEMS_B MSI1, BOM_INVENTORY_COMPONENTS  BIC, " +
			" BOM_SUBSTITUTE_COMPONENTS BSC , MTL_SYSTEM_ITEMS_B MSI2 " +
			" WHERE  MSI1.INVENTORY_ITEM_ID = BIC.COMPONENT_ITEM_ID " +
			" AND BIC.COMPONENT_SEQUENCE_ID = BSC.COMPONENT_SEQUENCE_ID " +
			" AND BSC.SUBSTITUTE_COMPONENT_ID = MSI2.INVENTORY_ITEM_ID " +
			" AND MSI2.SEGMENT1 ='" + altermatePartNum + "'" ;
		
		logger.debug(sql);
		
    	ParameterizedBeanPropertyRowMapper<PartNumElementTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<PartNumElementTo>();
    	rowMapper.setMappedClass(PartNumElementTo.class);

    	List<PartNumElementTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
	}
	
	public List<ErpBOMTo> getBOMbyAssembly(String assembly) throws DataAccessException{
			
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			String sql  = " SELECT DISTINCT MSI.SEGMENT1 ASSEMBLY, " + 
					" MSI.DESCRIPTION  ASSEMBLY_DESCRIPTION, " +  
					" MSI.PRIMARY_UOM_CODE ASSEMBLY_UOM, " +
					" MSI1.SEGMENT1 ITEM, " +
					//" BIC.ITEM_NUM, " +
					" BIC.OPERATION_SEQ_NUM, " +
					" MSI1.DESCRIPTION ITEM_DESCRIPTION, " +
					" MSI1.PRIMARY_UOM_CODE ITEM_UOM " +

					" FROM MTL_SYSTEM_ITEMS_B MSI, " +
					" BOM_BILL_OF_MATERIALS BOM, " +
					" BOM_INVENTORY_COMPONENTS BIC, " +
					" MTL_SYSTEM_ITEMS_B MSI1 " +
					" WHERE 1 = 1 " +
					" AND MSI.INVENTORY_ITEM_ID = BOM.ASSEMBLY_ITEM_ID " + 
					" AND MSI.ORGANIZATION_ID = BOM.ORGANIZATION_ID " +
            
					" AND BOM.ALTERNATE_BOM_DESIGNATOR IS NULL " + 
					" AND BOM.BILL_SEQUENCE_ID = BIC.BILL_SEQUENCE_ID " +  
            
					" AND BIC.DISABLE_DATE IS NULL " +
					" AND BIC.COMPONENT_ITEM_ID = MSI1.INVENTORY_ITEM_ID " +
					//--由父及子  
					"AND MSI.SEGMENT1 ='" + assembly+ "'";
       //AND MSI1.SEGMENT1 IN ('11460150-A0') --由子及父 " ;
			
			logger.debug(sql);
	    	ParameterizedBeanPropertyRowMapper<ErpBOMTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<ErpBOMTo>();
	    	rowMapper.setMappedClass(ErpBOMTo.class);

	    	List<ErpBOMTo> result = sjt.query(sql,rowMapper, new Object[] {} );
			
			if (result != null && result.size() > 0) {
				return result;
			} else {
				return null;
			}	
	}
	
	
	public List<ErpBOMTo> getBOMbyItem(String item) throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		String sql  = " SELECT DISTINCT MSI.SEGMENT1 ASSEMBLY, " + 
			" MSI.DESCRIPTION  ASSEMBLY_DESCRIPTION, " +  
			" MSI.PRIMARY_UOM_CODE ASSEMBLY_UOM, " +
			" MSI1.SEGMENT1 ITEM, " +
			//" BIC.ITEM_NUM, " +
			" BIC.OPERATION_SEQ_NUM, " +
			" MSI1.DESCRIPTION ITEM_DESCRIPTION, " +
			" MSI1.PRIMARY_UOM_CODE ITEM_UOM " +
	
			" FROM MTL_SYSTEM_ITEMS_B MSI, " +
			" BOM_BILL_OF_MATERIALS BOM, " +
			" BOM_INVENTORY_COMPONENTS BIC, " +
			" MTL_SYSTEM_ITEMS_B MSI1 " +
			" WHERE 1 = 1 " +
			" AND MSI.INVENTORY_ITEM_ID = BOM.ASSEMBLY_ITEM_ID " + 
			" AND MSI.ORGANIZATION_ID = BOM.ORGANIZATION_ID " +
	
			" AND BOM.ALTERNATE_BOM_DESIGNATOR IS NULL " + 
			" AND BOM.BILL_SEQUENCE_ID = BIC.BILL_SEQUENCE_ID " +  
	
			" AND BIC.DISABLE_DATE IS NULL " +
			" AND BIC.COMPONENT_ITEM_ID = MSI1.INVENTORY_ITEM_ID " +
			//--由子及父 
			" AND MSI1.SEGMENT1 ='" + item + "'";

		logger.debug(sql);
    	ParameterizedBeanPropertyRowMapper<ErpBOMTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<ErpBOMTo>();
    	rowMapper.setMappedClass(ErpBOMTo.class);

    	List<ErpBOMTo> result = sjt.query(sql,rowMapper, new Object[] {} );
		
		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}	
}
}
