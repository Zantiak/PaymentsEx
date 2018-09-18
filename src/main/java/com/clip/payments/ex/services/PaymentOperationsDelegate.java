package com.clip.payments.ex.services;

import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;

public class PaymentOperationsDelegate {
	
	private PaymentOperationsLookUp paymentOperationsLookUp = new PaymentOperationsLookUp();
	private PaymentOperationsService paymentOperationsService;
	
	public PaymentUser doOperation(PaymentUser paymentUser) throws PaymentOperationException {
		paymentOperationsService = paymentOperationsLookUp.getOperationService(paymentUser.getOperationEnum());
		return paymentOperationsService.executeOperation(paymentUser);
	}
}
