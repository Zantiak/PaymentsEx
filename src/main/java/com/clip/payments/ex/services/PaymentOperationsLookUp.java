package com.clip.payments.ex.services;

import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.services.impl.PaymentOperationAddService;
import com.clip.payments.ex.services.impl.PaymentOperationListService;
import com.clip.payments.ex.services.impl.PaymentOperationReadIdService;
import com.clip.payments.ex.services.impl.PaymentOperationSumService;

public class PaymentOperationsLookUp {
	
	public PaymentOperationsService getOperationService(OperationsEnum operationEnum) throws PaymentOperationException {
		
		switch(operationEnum) {
			case ADD:
				return new PaymentOperationAddService();
			case LIST :
				return new PaymentOperationListService();
			case CONSULT:
				return new PaymentOperationReadIdService();
			case SUM:
				return new PaymentOperationSumService();
			default:
				return null;
		}		
	}

}
