package com.cosine.cosiaries.model;

import java.util.Date;
import java.util.HashMap;

public class Row {
	Date date;
	Table table;
	HashMap<String,Cell> cells;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public HashMap<String, Cell> getCells() {
		return cells;
	}
	public void setCells(HashMap<String, Cell> cells) {
		this.cells = cells;
	}
}
