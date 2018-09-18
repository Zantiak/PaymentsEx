package com.clip.payments.ex.services.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import com.clip.payments.ex.daos.PaymentUserDAO;
import com.clip.payments.ex.daos.impl.PaymentUserDAOImpl;
import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.exceptions.PaymentUserDAOException;
import com.clip.payments.ex.services.PaymentOperationsService;

public class PaymentOperationSumService implements PaymentOperationsService {

	public PaymentUser executeOperation(PaymentUser paymentUser) throws PaymentOperationException {

		PaymentUserDAO paymentUserDAO = new PaymentUserDAOImpl();
		PaymentUser paymentUserRet = new PaymentUser();
		TreeMap<String, Payment> operationsSet = new TreeMap<String, Payment>();
		BigDecimal paymentsSum = BigDecimal.ZERO;

		try {
			operationsSet = paymentUserDAO.listPayments(paymentUser);
			for (Map.Entry<String, Payment> entry : operationsSet.entrySet()) {
				paymentsSum = paymentsSum.add(entry.getValue().getAmount());
			} 
			//convert map into TreeSet<payment>			
			paymentUserRet.setOperationEnum(paymentUser.getOperationEnum());
			paymentUserRet.setPaymentsSum(paymentsSum);
			paymentUserRet.setUserId(paymentUser.getUserId());
			
		} catch (PaymentUserDAOException e1) {
			throw new PaymentOperationException(e1.getMessage());
		}
		return paymentUserRet;
	}

}
