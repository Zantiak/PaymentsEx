package com.clip.payments.ex.services.impl;

import java.util.TreeMap;
import java.util.UUID;

import com.clip.payments.ex.daos.PaymentUserDAO;
import com.clip.payments.ex.daos.impl.PaymentUserDAOImpl;
import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.exceptions.PaymentUserDAOException;
import com.clip.payments.ex.services.PaymentOperationsService;

public class PaymentOperationAddService implements PaymentOperationsService {

	public PaymentUser executeOperation(final PaymentUser paymentUser) throws PaymentOperationException {
		
		PaymentUserDAO paymentUserDAO = new PaymentUserDAOImpl();
		
		//generate uuid	
		UUID uuid = UUID.randomUUID();
		final Payment newPayment = new Payment(paymentUser.getPayment().getAmount(), paymentUser.getPayment().getDescription(),
				paymentUser.getPayment().getDate(), uuid, paymentUser.getUserId());
		final PaymentUser newPaymentUserOperation = new PaymentUser(paymentUser.getUserId(), newPayment, null, null,
				paymentUser.getOperationEnum());
		TreeMap<String, Payment> operationsSet = new TreeMap<String, Payment>();
		
		try {
			operationsSet = paymentUserDAO.listPayments(paymentUser);
		} catch (PaymentUserDAOException e1) {
			if (!e1.getMessage().equals("User doesn't exist")) {
				throw new PaymentOperationException(e1.getMessage());
			}
		}
		
		try {
			// validate if the transaction already exist
			if (operationsSet.containsKey(uuid.toString())) {
				throw new PaymentOperationException();
			} else {
				paymentUserDAO.addPayment(newPaymentUserOperation);
			}
		} catch (PaymentUserDAOException e) {
			throw new PaymentOperationException(e.getMessage());
		}
		return newPaymentUserOperation;
	}
	


}
