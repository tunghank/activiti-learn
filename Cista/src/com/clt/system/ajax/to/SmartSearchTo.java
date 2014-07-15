package com.clt.system.ajax.to;

import java.util.ArrayList;
import java.util.List;

public class SmartSearchTo extends com.clt.system.util.BaseObject implements
							java.io.Serializable {
    private String name;
    private String title;
    private String table;
    private String keyColumn;
    private int mode;
    private List<String> columns;
    private String whereCause;
    private String orderBy;
    private String like;
	// Constructors

	/** default constructor */
	public SmartSearchTo() {
	}
    
    public List<String> getColumns() {
        return columns;
    }
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
    public String getKeyColumn() {
        return keyColumn;
    }
    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getMode() {
        return mode;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public String getWhereCause() {
        return whereCause;
    }
    public void setWhereCause(String whereCause) {
        this.whereCause = whereCause;
    }

    public void addColumn(String column) {
        if (columns == null) {
            columns = new ArrayList<String>();
        }

        columns.add(column);
    }

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}
    
    
}
