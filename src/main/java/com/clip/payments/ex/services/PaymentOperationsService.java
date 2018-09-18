package com.clip.payments.ex.services;

import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;

public interface PaymentOperationsService {
	public PaymentUser executeOperation(final PaymentUser paymentUser) throws PaymentOperationException;
}
