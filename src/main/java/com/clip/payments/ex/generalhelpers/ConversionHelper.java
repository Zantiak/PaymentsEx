package com.clip.payments.ex.generalhelpers;

import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.ValidationException;

public interface ConversionHelper {
	public PaymentUser convertInput (String sInput) throws ValidationException;
	public String objToJsonString(Object object);
}
