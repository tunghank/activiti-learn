package com.clt.system.ajax.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.clt.system.util.BaseErpDao;
import com.clt.system.ajax.to.SmartSearchTo;

public class SmartSearchDao extends BaseErpDao {
    public List<Map<String, Object>> find(SmartSearchTo ss, String inputFieldValue) {
    	
    	logger.debug(inputFieldValue);
    	
        SimpleJdbcTemplate sjt = getSimpleJdbcTemplate();
        String filterEmpty = "";
        String sql = "select ";
        
        logger.debug("ss.getColumns() " + ss.getColumns().toString());
        if (ss.getColumns() == null || ss.getColumns().size() == 0) {
            sql += "distinct " + ss.getKeyColumn() + "";
            filterEmpty = getAssertEmptyString(ss.getKeyColumn());
        } else if (ss.getColumns() != null && ss.getColumns().size() == 1) {
            sql += "distinct " + ss.getColumns().get(0) + "";
            filterEmpty = getAssertEmptyString(ss.getColumns().get(0));
        } else {
            List<String> cols = ss.getColumns();
            String distStr = "";
            if (cols != null) {
                for (String s : cols) {
                    distStr += "," + s;
                }
            }
            if (distStr.length() > 0) {
                distStr = distStr.substring(1);
            }
            
            sql += "distinct " + distStr + "";
            //filterEmpty = getAssertEmptyString(ss.getColumns().get(0));            
            //sql += "distinct *";
        }

        sql += " from " + ss.getTable() + " where 1=1 ";

        if (filterEmpty.length() > 0) {
            sql += " and " + filterEmpty;
        }
        
        logger.debug("Like " + ss.getLike());
        
        
        if (inputFieldValue != null && inputFieldValue.length() > 0) {
        	if ( ss.getLike().equals("1")){
        		sql += " and " +ss.getKeyColumn() +" like "+ super.getLikeSQL(inputFieldValue.toUpperCase());
        	}else{
        		sql += " and " +ss.getKeyColumn() +"='"+ inputFieldValue.toUpperCase()+"'";
        	}
        }

        if (ss.getWhereCause() != null && ss.getWhereCause().length() > 0) {
        	logger.debug("ss.getWhereCause() " + ss.getWhereCause());
            sql += " and " + ss.getWhereCause();
        }

        if (ss.getOrderBy() != null && ss.getOrderBy().length() > 0) {
            sql += " order by " + ss.getOrderBy();
        } else {
            sql += " order by " + ss.getKeyColumn();
        }

        logger.debug( "sql " + sql);
        return sjt.queryForList(sql, new Object[]{});
    }
}
