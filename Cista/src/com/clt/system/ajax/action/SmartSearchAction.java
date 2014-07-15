package com.clt.system.ajax.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.clt.system.ajax.dao.SmartSearchDao;
import com.clt.system.ajax.to.SmartSearchTo;
import com.clt.system.util.BaseAction;

public class SmartSearchAction extends BaseAction {
	
	String columns;
	String like;
	
    public String smartSearch() throws Exception {
    	logger.debug("smartSearch start");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1);
        }
        
        String forward = super.SUCCESS;
        SmartSearchTo ss = new SmartSearchTo();
        
        logger.debug("request.getParameter('name') " + request.getParameter("name"));
        if (request.getParameter("name") != null && request.getParameter("name").length() > 0) {
            ss = SmartSearchLoader.getSmartSearch(request.getParameter("name"));
            ss =null!=ss?ss:new SmartSearchTo();
            //request.setAttribute("name", request.getParameter("name"));
        } else {
            ss.setTable(request.getParameter("table"));
            //request.setAttribute("table", request.getParameter("table"));
            ss.setKeyColumn(request.getParameter("keyColumn"));
            //request.setAttribute("keyColumn", request.getParameter("keyColumn"));
            String columnsStr = request.getParameter("columns");
            logger.debug("columnsStr " + columnsStr);
            if (columnsStr != null && columnsStr.length() > 0) {
                String[] columns = columnsStr.split(",");
                for (String column : columns) {
                    ss.addColumn(column.trim());
                }
            }
        }

        if (request.getParameter("keyColumn") != null && request.getParameter("keyColumn").length() > 0) {
            ss.setKeyColumn(request.getParameter("keyColumn"));
            //request.setAttribute("title", request.getParameter("title"));
        }
        
        if(ss.getLike() == null || ss.getColumns().size() == 0){
        	ss.setLike(this.like);
        }
        
        if (ss.getColumns() == null || ss.getColumns().size() == 0) {
        	
        	String columnsStr = this.columns;
        	columnsStr = null != columnsStr ? columnsStr : "";
        	logger.debug("columnsStr " + this.columns);
        	logger.debug("columnsStr " + columnsStr);
        	
        	if( !columns.equals("") ){
                String[] columns = columnsStr.split(",");
                for (String column : columns) {
                    ss.addColumn(column.trim());
                }
        	}else{
        		ss.addColumn(ss.getKeyColumn());
        	}
            logger.debug("columnsStr " + ss.getColumns().toString());
        }
       
        if (request.getParameter("table") != null && request.getParameter("table").length() > 0) {
            ss.setTable(request.getParameter("table"));
            //request.setAttribute("title", request.getParameter("title"));
        }
        
        if (request.getParameter("title") != null && request.getParameter("title").length() > 0) {
            ss.setTitle(request.getParameter("title"));
            //request.setAttribute("title", request.getParameter("title"));
        }
        if (request.getParameter("mode") != null && request.getParameter("mode").length() > 0) {
            ss.setMode(Integer.parseInt(request.getParameter("mode")));
            //request.setAttribute("mode", request.getParameter("mode"));
        }

        if (request.getParameter("whereCause") != null && request.getParameter("whereCause").length() > 0) {
            ss.setWhereCause(request.getParameter("whereCause"));
            //request.setAttribute("whereCause", request.getParameter("whereCause"));
        }

        if (request.getParameter("orderBy") != null && request.getParameter("orderBy").length() > 0) {
            ss.setOrderBy(request.getParameter("orderBy"));
            //request.setAttribute("orderBy", request.getParameter("orderBy"));
        }

        request.setAttribute("inputField", request.getParameter("inputField"));
        request.setAttribute("inputFieldValue", request.getParameter("inputFieldValue"));
        request.setAttribute("callbackHandle", request.getParameter("callbackHandle"));

        try {
        	String inputFieldValue = request.getParameter("inputFieldValue");
        	inputFieldValue = null != inputFieldValue ? inputFieldValue : "";
        	logger.debug( "request.getParameter('inputFieldValue') " + inputFieldValue );
        	logger.debug("columnsStr " + ss.getColumns().toString());
        	if( inputFieldValue.equals("")){
        		logger.debug("1 result size 0");
        		request.setAttribute("result", new ArrayList<Map<String, Object>>());
        	}else{
        		List<Map<String, Object>> result = new SmartSearchDao().find(ss, inputFieldValue);
        		logger.debug("2 result size " + result.size() );
        		request.setAttribute("result", result);
        	}
        } catch (RuntimeException e) {
            logger.error("Smart search query exception.", e);
        }
        request.setAttribute("SmartSearch", ss);
        logger.debug("smartSearch End");
        return forward;
    }

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}


}
