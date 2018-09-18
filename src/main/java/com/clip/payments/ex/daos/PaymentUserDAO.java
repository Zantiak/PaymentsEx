package com.clip.payments.ex.daos;

import java.util.TreeMap;

import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentUserDAOException;

public interface PaymentUserDAO {
	
	public void addPayment(final PaymentUser PaymentUser) throws PaymentUserDAOException;
	public Payment showPayment(final PaymentUser PaymentUser) throws PaymentUserDAOException;
	public TreeMap<String, Payment> listPayments(final PaymentUser PaymentUser) throws PaymentUserDAOException;

}
