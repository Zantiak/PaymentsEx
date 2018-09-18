package com.clip.payments.ex.services.test;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.services.PaymentOperationsLookUp;
import com.clip.payments.ex.services.impl.PaymentOperationAddService;
import com.clip.payments.ex.services.impl.PaymentOperationListService;
import com.clip.payments.ex.services.impl.PaymentOperationReadIdService;
import com.clip.payments.ex.services.impl.PaymentOperationSumService;

public class PaymentOperationsLookUpTest {
	
	final PaymentOperationsLookUp polu = new PaymentOperationsLookUp();
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();

	@Test
	public void getOperationServiceAdd() throws PaymentOperationException {
		assertEquals(PaymentOperationAddService.class, polu.getOperationService(OperationsEnum.ADD).getClass());
	}
	
	@Test
	public void getOperationServiceReadId() throws PaymentOperationException {
		assertEquals(PaymentOperationReadIdService.class, polu.getOperationService(OperationsEnum.CONSULT).getClass());
	}
	
	@Test
	public void getOperationServiceSum() throws PaymentOperationException {
		assertEquals(PaymentOperationSumService.class, polu.getOperationService(OperationsEnum.SUM).getClass());
	}
	
	@Test
	public void getOperationServiceList() throws PaymentOperationException {
		assertEquals(PaymentOperationListService.class, polu.getOperationService(OperationsEnum.LIST).getClass());
	}
	
	@Ignore
	@Test
	public void getOperationServiceIncorrect() throws PaymentOperationException {
        thrown.expect(PaymentOperationException.class);
        thrown.expectMessage("An error occurred during an operation");

		polu.getOperationService(null);
	}	

}
