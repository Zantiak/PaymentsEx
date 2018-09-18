package com.clip.payments.ex.services.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.services.PaymentOperationsDelegate;
import com.clip.payments.ex.utils.DateUtil;

public class PaymentOperationsDelegateTest {
	
	final PaymentOperationsDelegate pod = new PaymentOperationsDelegate();
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
    @Ignore
	@Test
	public void getOperationServiceAdd() throws PaymentOperationException, ParseException {
		Payment payment = new Payment(new BigDecimal(1), "unit Testing", 
				DateUtil.stringToDate("2018-09-17"), null, "1");
		PaymentUser paymentUser = new PaymentUser("1", payment, null, null, OperationsEnum.ADD);
		assertEquals(PaymentUser.class, pod.doOperation(paymentUser).getClass());
	}
	
    @Ignore
	@Test
	public void getOperationServiceIncorrectId() throws PaymentOperationException, ParseException {
		Payment payment = new Payment(new BigDecimal(1), "unit Testing", 
				DateUtil.stringToDate("2018-09-18"), null, "1");
		PaymentUser paymentUser = new PaymentUser("1", payment, null, null, OperationsEnum.ADD);
        thrown.expect(PaymentOperationException.class);
        thrown.expectMessage("An error occurred during an operation");
		pod.doOperation(paymentUser);
	}	

}
