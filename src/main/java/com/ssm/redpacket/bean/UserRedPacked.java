package com.ssm.redpacket.bean;

import java.sql.Timestamp;

public class UserRedPacked {
     private long id;
     private long redPacketId;
     private long userId;
     private Double amount;
     private Timestamp grabTime;
     private String note;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(long redPacketId) {
		this.redPacketId = redPacketId;
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
	public Timestamp getGrabTime() {
		return grabTime;
	}
	public void setGrabTime(Timestamp grabTime) {
		this.grabTime = grabTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "UserRedPacked [id=" + id + ", redPacketId=" + redPacketId + ", userId=" + userId + ", amount=" + amount
				+ ", grabTime=" + grabTime + ", note=" + note + "]";
	}
     
}
