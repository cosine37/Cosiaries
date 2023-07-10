package com.cosine.cosiaries.model;

import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class Table {
	String username;
	List<String> columnNames;
	HashMap<Date, Row> rows;
	Date earliestDate;
	Date latestDate;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public HashMap<Date, Row> getRows() {
		return rows;
	}
	public void setRows(HashMap<Date, Row> rows) {
		this.rows = rows;
	}
	public Date getEarliestDate() {
		return earliestDate;
	}
	public void setEarliestDate(Date earliestDate) {
		this.earliestDate = earliestDate;
	}
	public Date getLatestDate() {
		return latestDate;
	}
	public void setLatestDate(Date latestDate) {
		this.latestDate = latestDate;
	}
	
	
}
