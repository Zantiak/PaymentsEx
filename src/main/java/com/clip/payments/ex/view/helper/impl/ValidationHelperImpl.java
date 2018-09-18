package com.clip.payments.ex.view.helper.impl;

import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.ValidationException;
import com.clip.payments.ex.utils.Constants;
import com.clip.payments.ex.view.helper.ValidationHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ValidationHelperImpl implements ValidationHelper{
	
	private final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"; 

	
	
	public boolean validateEntry(final String sInput) throws ValidationException {
		if (sInput.length() < 5 || !sInput.contains(Constants.CHAR_SPACE))
			throw new ValidationException();
		else 
			return true;
	}

	
	
	public boolean validateUUID(String uuidStr) {
		return uuidStr.matches(UUID_REGEX);
	}

	
	
	public JsonObject validateJSON(String jsonStr) {
		JsonParser jsonParser = new JsonParser();
		JsonObject objectFromString = jsonParser.parse(jsonStr).getAsJsonObject();				 
		return objectFromString;
	}

	
	
	public OperationsEnum validateOperation(String operation) throws ValidationException {
		try {
			return OperationsEnum.valueOf(operation.toUpperCase());
		} catch (IllegalArgumentException iae) {
			throw new ValidationException("Incorrect Operation command");
		}	
	}
	
	
	
	public boolean isNumericAndNotZero(String value) {
		try {
			int numberExpected = Integer.parseInt(value);	
			if(numberExpected > 0)
				return true;
		} catch (NumberFormatException e) {
			return false;
		}
		return false;		
	}

}
