package com.clip.payments.ex.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Payment {
	
	public Payment(BigDecimal amount, String description, Date date, UUID transaction_id, String userId){
		this.amount = amount;
		this.description = description;
		this.date = date;
		this.transaction_id = transaction_id;
		this.userId = userId;
	}
	
	public Payment(){
	}
	
	private UUID transaction_id;
	private BigDecimal amount;
	private String description;
	private Date date;
	private String userId;
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UUID getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(UUID transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
