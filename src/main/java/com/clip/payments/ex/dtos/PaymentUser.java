package com.clip.payments.ex.dtos;

import java.math.BigDecimal;
import java.util.Set;

import com.clip.payments.ex.enums.OperationsEnum;

public class PaymentUser {

	public PaymentUser(String userId, Payment payment, Set<Payment> payments, BigDecimal paymentSum, OperationsEnum operationEnum){
		this.userId = userId;
		this.payment = payment;
		this.payments = payments;
		this.paymentsSum = paymentSum;
		this.operationEnum = operationEnum;
	}
	
	public PaymentUser(){
	}
	
	private String userId;
	private OperationsEnum operationEnum;
	private Payment payment;
	private Set<Payment> payments;
	private BigDecimal paymentsSum;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Set<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	public BigDecimal getPaymentsSum() {
		return paymentsSum;
	}
	public void setPaymentsSum(BigDecimal paymentsSum) {
		this.paymentsSum = paymentsSum;
	}

	public OperationsEnum getOperationEnum() {
		return operationEnum;
	}

	public void setOperationEnum(OperationsEnum operationEnum) {
		this.operationEnum = operationEnum;
	}
	
}
