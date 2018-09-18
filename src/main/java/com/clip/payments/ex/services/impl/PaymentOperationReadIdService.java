package com.clip.payments.ex.services.impl;

import com.clip.payments.ex.daos.PaymentUserDAO;
import com.clip.payments.ex.daos.impl.PaymentUserDAOImpl;
import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.exceptions.PaymentUserDAOException;
import com.clip.payments.ex.services.PaymentOperationsService;

public class PaymentOperationReadIdService implements PaymentOperationsService {

	public PaymentUser executeOperation(PaymentUser paymentUser) throws PaymentOperationException {

		PaymentUserDAO paymentUserDAO = new PaymentUserDAOImpl();
		PaymentUser paymentUserRet = new PaymentUser();
		Payment payment = null;

		try {
			payment = paymentUserDAO.showPayment(paymentUser);
						
			paymentUserRet.setPayment(payment);
			paymentUserRet.setOperationEnum(paymentUser.getOperationEnum());
			
		} catch (PaymentUserDAOException e1) {
			throw new PaymentOperationException(e1.getMessage());
		}
		return paymentUserRet;
	}

}
