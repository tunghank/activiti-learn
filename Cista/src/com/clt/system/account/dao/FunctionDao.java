package com.clt.system.account.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.system.util.BaseDao;
import com.clt.system.to.SysFunctionTo;
import com.clt.system.to.SysRoleFunctionTo;

public class FunctionDao extends BaseDao{	
	
	public int updateFunction(SysFunctionTo curFunction) throws DataAccessException{			
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " UPDATE SYS_FUNCTION A "
			+ "	SET "
			+ "		A.parent_id 	= ? "			
			+ "		,A.TITLE 		= ? "
			+ "		,A.CLS 			= ? "
			+ "     ,A.LEAF 		= ? "
			+ "     ,A.URL 			= ? "
			+ "     ,A.HREF_TARGET	= ? "
			+ "	WHERE  A.ID 		= ? ";		
		logger.debug(sql);
		
		int answer = sjt.update( sql	, new Object[]{	
				curFunction.getParentId() ,curFunction.getTitle()
				,curFunction.getCls()	  ,curFunction.getLeaf()
				,curFunction.getUrl()     ,curFunction.getHrefTarget()
				,curFunction.getId()
		});
		
		return answer;	
	}
	
	public int deleteFunction(String functionId) throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		sql = " DELETE FROM SYS_FUNCTION A WHERE a.id= ? ";		
		logger.debug(sql);
		
		int answer = sjt.update(sql, new Object[] {functionId });
		return answer ;	
	}
	
	public int insertFunction(SysFunctionTo newFunction)throws DataAccessException{
		String sql ="";
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();	
		
		sql = " INSERT INTO SYS_Function(ID,PARENT_ID,TITLE,CLS,LEAF,URL,HREF_TARGET)"
			+ "	VALUES ( SYS_FUNCTION_SEQ.NEXTVAL ,?,?,?,?,?,?)" ;
		logger.debug(sql);
		
		int answer = sjt.update(sql , new Object[]{
				newFunction.getParentId() ,newFunction.getTitle()
				,newFunction.getCls()     ,newFunction.getLeaf()
				,newFunction.getUrl()     ,newFunction.getHrefTarget()
			});
		return answer ;		
	}
	
	// 取得 function 細部資料 by parentId , functionName
	public SysFunctionTo getFunctionDetail(String parentId , String functionName) throws DataAccessException{	
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
	    	rowMapper.setMappedClass(SysFunctionTo.class);
			String sql ="";
			
			sql = " SELECT "
				+ "    A.ID "
				+ "    ,A.PARENT_ID "
				+ "    ,A.TITLE "
				+ "    ,A.CLS "
				+ "    ,A.LEAF " 
				+ "    ,A.URL "
				+ "    ,A.HREF_TARGET "
				+ "    ,A.CDT "
				+ " FROM SYS_FUNCTION A "
				+ " WHERE "
                + "		1 = 1 "
                + "     AND PARENT_ID = ? "
                + "     AND TITLE = ? "
                + " ORDER BY ID " ;
			
	    	List<SysFunctionTo> result = sjt.query(sql,rowMapper, new Object[]{parentId ,functionName});
			
			if (result != null && result.size() > 0){return result.get(0);} 
			else{return null;}			
	}
	
	// 取得 function 細部資料 by function id
	public SysFunctionTo getFunctionDetail(String Id ) throws DataAccessException{
			SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
			ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
	    		new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
	    	rowMapper.setMappedClass(SysFunctionTo.class);

			String sql ="";
			
			sql = " SELECT "
				+ "    A.ID "
				+ "    ,A.PARENT_ID "
				+ "    ,A.TITLE "
				+ "    ,A.CLS "
				+ "    ,A.LEAF " 
				+ "    ,A.URL "
				+ "    ,A.HREF_TARGET "
				+ "    ,A.CDT "
				+ " FROM SYS_FUNCTION A "
				+ " WHERE "
                + "		1 = 1 "
                + "     AND A.ID = ? "
                + " ORDER BY ID " ;
	    	
	    	List<SysFunctionTo> result = sjt.query(sql,rowMapper, new Object[]{Id});
			
			if (result != null && result.size() > 0) {return result.get(0);} 
			else {return null;}			
	}
	
	public List searchFunctionList(String parentId , String cls , String functionName)throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
			new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
		rowMapper.setMappedClass(SysFunctionTo.class);
		
		String sql ="";
		
		sql = " SELECT "
			+ "     A.TITLE AS parentName "
			+ "     ,B.PARENT_ID "
			+ "     ,B.TITLE "
			+ "     ,B.ID"
			+ "     ,B.CLS "
			+ "     ,B.LEAF "
			+ "     ,B.URL "
			+ "     ,B.HREF_TARGET "
			+ " FROM "
			+ "     SYS_FUNCTION  A "
			+ "     ,SYS_FUNCTION B "
			+ " WHERE " 
			+ "     A.ID = B.PARENT_ID " ;
		
			// parentId
			if (parentId.equals("")){ 
				parentId = "1";	sql += " And 1 = ?";
			}else{
				parentId = parentId.replace('*','%');
			
				if (parentId.contains("%")) 	sql += " And B.PARENT_ID like ? ";
				else 							sql += " And B.PARENT_ID = ? ";			
			}
		
			// cls
			if (cls.equals("")){ 
				cls = "1";	sql += " And 1 = ?";
			}else  sql += " And B.CLS = ? ";
		
			// title (子title)
			if (functionName.equals("")){ 
				functionName = "1";	sql += " And 1 = ?";
			}else{
				functionName = functionName.replace('*','%');
			
				if (functionName.contains("%")) 	sql += " And B.TITLE like ? ";
				else 							sql += " And B.TITLE = ? ";			
			}	
		
		sql	+= " ORDER BY B.LEAF,B.PARENT_ID ,B.TITLE ";		
		
		List<SysFunctionTo> result = sjt.query(sql,rowMapper, new Object[] {parentId,cls,functionName} );
	
		if (result != null && result.size() > 0){
			return result ;
		}else{	
			return null;
		}
	}

	// 取得限定的 function/parent name
	public List getClsFunction(String type)throws DataAccessException{
		
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
    	rowMapper.setMappedClass(SysFunctionTo.class);
    	
		String sql ="";
		
		sql = " SELECT "
			+ "    A.ID "
			+ "    ,A.PARENT_ID "
			+ "    ,A.TITLE "
			+ "    ,A.CLS "
			+ "    ,A.LEAF " 
			+ "    ,A.URL "
			+ "    ,A.HREF_TARGET "
			+ "    ,A.CDT "
			+ " FROM SYS_FUNCTION A "
			+ " WHERE 1=1 " ;		
			
			// leaf
			if (type.equals("")){ 
				type = "1";	sql += " And 1 = ?";
			}else  sql += " And A.LEAF = ? ";
			
		
		sql+= " ORDER BY A.LEAF,A.TITLE" ;		
    	
    	List<SysFunctionTo> result = sjt.query(sql,rowMapper, new Object[] {type} );
		
		if (result != null && result.size() > 0){return result ;} 
		else{return null;}
	}
	
	// Get all child for delete
	public List getAllChild(String id) throws DataAccessException{
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		ParameterizedBeanPropertyRowMapper<SysFunctionTo> rowMapper = 
    		new ParameterizedBeanPropertyRowMapper<SysFunctionTo>();
    	rowMapper.setMappedClass(SysFunctionTo.class);
    	
		String sql ="";
		
		sql = " SELECT ID, PARENT_ID, TITLE "
			+ " FROM SYS_FUNCTION "
			+ " CONNECT BY PRIOR ID = PARENT_ID "
			+ " START WITH ID = ? "
			+ " ORDER BY ID DESC " ;
		logger.debug(sql);
    	
    	List<SysFunctionTo> result = sjt.query(sql,rowMapper, new Object[] {id} );
		
		if (result != null && result.size() > 0){return result ;} 
		else{return null;}
	}
}
