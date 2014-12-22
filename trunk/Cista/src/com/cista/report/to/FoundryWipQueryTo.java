package com.cista.report.to;


public class FoundryWipQueryTo {
	
	private  String start;
	private  String limit;
	private  String cistaProject;
	private  String lot;
	
	public FoundryWipQueryTo() {

	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getCistaProject() {
		return cistaProject;
	}

	public void setCistaProject(String cistaProject) {
		this.cistaProject = cistaProject;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}



}
