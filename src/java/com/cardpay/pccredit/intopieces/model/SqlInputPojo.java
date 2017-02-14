package com.cardpay.pccredit.intopieces.model;

public class SqlInputPojo {
	private String rowindex;
	private String owner;
	private String tableName;
	private String tablespaceName;
	private boolean checked = true;
	
	public String getRowindex() {
		return rowindex;
	}
	public void setRowindex(String rowindex) {
		this.rowindex = rowindex;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTablespaceName() {
		return tablespaceName;
	}
	public void setTablespaceName(String tablespaceName) {
		this.tablespaceName = tablespaceName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
