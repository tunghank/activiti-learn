package com.cista.system.sys.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cista.system.to.SysFunParameterTo;
import com.cista.system.util.BaseDao;


public class SysFunParameterDao extends BaseDao {

	public SysFunParameterTo getParameterByKey(String funName, String funFieldName, 
					String fieldValue, String item) throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
		
		ParameterizedBeanPropertyRowMapper<SysFunParameterTo> rowMapper = 
					new ParameterizedBeanPropertyRowMapper<SysFunParameterTo>();
		rowMapper.setMappedClass(SysFunParameterTo.class);
		
		String sql =  " SELECT A.FUN_NAME, A.FUN_FIELD_NAME, A.FIELD_VALUE, " +
				" A.ITEM, A.FIELD_SHOW_NAME, A.CDT " +
				" FROM SYS_FUN_PARAMETER_VALUE A Where A.FUN_NAME = ? " +
				" AND A.FUN_FIELD_NAME = ? AND A.FIELD_VALUE = ? " +
				" AND A.ITEM = ?" ;
		

    	logger.debug(sql);
		List<SysFunParameterTo> result = sjt.query(sql, rowMapper, 
					new Object[] {funName, funFieldName, fieldValue, item});

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public SysFunParameterTo getParameterByKey(String funName,
			String funFieldName, String fieldValue)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		ParameterizedBeanPropertyRowMapper<SysFunParameterTo> rowMapper = new ParameterizedBeanPropertyRowMapper<SysFunParameterTo>();
		rowMapper.setMappedClass(SysFunParameterTo.class);

		String sql = " SELECT A.FUN_NAME, A.FUN_FIELD_NAME, A.FIELD_VALUE, "
				+ " A.ITEM, A.FIELD_SHOW_NAME, A.CDT "
				+ " FROM SYS_FUN_PARAMETER_VALUE A Where A.FUN_NAME = ? "
				+ " AND A.FUN_FIELD_NAME = ? AND A.FIELD_VALUE = ? ";

		logger.debug(sql);
		List<SysFunParameterTo> result = sjt.query(sql, rowMapper,
				new Object[] { funName, funFieldName, fieldValue });

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public List<SysFunParameterTo> getParameterList(String funName, String funFieldName )
			throws DataAccessException {
		// TODO Auto-generated method stub
		SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();

		ParameterizedBeanPropertyRowMapper<SysFunParameterTo> rowMapper = new ParameterizedBeanPropertyRowMapper<SysFunParameterTo>();
		rowMapper.setMappedClass(SysFunParameterTo.class);

		String sql = " SELECT A.FUN_NAME, A.FUN_FIELD_NAME, A.FIELD_VALUE, "
				+ " A.ITEM, A.FIELD_SHOW_NAME, A.CDT "
				+ " FROM SYS_FUN_PARAMETER_VALUE A Where A.FUN_NAME = ? "
				+ " AND A.FUN_FIELD_NAME = ? " 
				+ " Order by A.ITEM";

		logger.debug(sql);
		List<SysFunParameterTo> result = sjt.query(sql, rowMapper,
				new Object[] { funName, funFieldName });

		if (result != null && result.size() > 0) {
			return result;
		} else {
			return null;
		}
	}
}
