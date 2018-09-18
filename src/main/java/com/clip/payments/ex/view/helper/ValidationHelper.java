package com.clip.payments.ex.view.helper;

import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.ValidationException;
import com.google.gson.JsonObject;

public interface ValidationHelper {
	public boolean validateEntry(String sInput) throws ValidationException;
	
	public boolean validateUUID(String uuidStr);
	public JsonObject validateJSON(String jsonStr);
	public OperationsEnum validateOperation(String operation) throws ValidationException;
	public boolean isNumericAndNotZero(String value);
}
