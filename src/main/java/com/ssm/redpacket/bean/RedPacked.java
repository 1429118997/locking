package com.ssm.redpacket.bean;

import java.sql.Timestamp;

public class RedPacked {
    private long redPackedId;
    private long userId;
    private Double amount;
    private Timestamp sendTime;
    private Integer total;
    private Integer stock;
    private Double unitAmount;
    private long version;
    private String note;
	public long getRedPackedId() {
		return redPackedId;
	}
	public void setRedPackedId(long redPackedId) {
		this.redPackedId = redPackedId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Double getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(Double unitAmount) {
		this.unitAmount = unitAmount;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
    
}
