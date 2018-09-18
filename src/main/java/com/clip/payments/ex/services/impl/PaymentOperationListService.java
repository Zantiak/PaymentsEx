package com.clip.payments.ex.services.impl;

import java.util.TreeMap;
import java.util.TreeSet;

import com.clip.payments.ex.daos.PaymentUserDAO;
import com.clip.payments.ex.daos.impl.PaymentUserDAOImpl;
import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.exceptions.PaymentUserDAOException;
import com.clip.payments.ex.services.PaymentOperationsService;
import com.clip.payments.ex.utils.DateComparator;

public class PaymentOperationListService implements PaymentOperationsService {

	
	
	public PaymentUser executeOperation(PaymentUser paymentUser) throws PaymentOperationException {

		PaymentUserDAO paymentUserDAO = new PaymentUserDAOImpl();
		PaymentUser paymentUserRet = new PaymentUser();
		TreeMap<String, Payment> operationsSet = new TreeMap<String, Payment>();
		TreeSet<Payment> paymentsSet = new TreeSet<Payment>(new DateComparator());

		try {
			operationsSet = paymentUserDAO.listPayments(paymentUser);
			
			//convert map into TreeSet<payment>
			paymentsSet.addAll(operationsSet.values());
			
			paymentUserRet.setPayments(paymentsSet);
			paymentUserRet.setOperationEnum(paymentUser.getOperationEnum());
			
		} catch (PaymentUserDAOException e1) {
			throw new PaymentOperationException(e1.getMessage());
		}
		return paymentUserRet;
	}

}
