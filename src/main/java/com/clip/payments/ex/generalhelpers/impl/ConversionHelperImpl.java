package com.clip.payments.ex.generalhelpers.impl;

import java.text.ParseException;
import java.util.UUID;

import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.enums.OperationsEnum;
import com.clip.payments.ex.exceptions.ValidationException;
import com.clip.payments.ex.generalhelpers.ConversionHelper;
import com.clip.payments.ex.utils.DateUtil;
import com.clip.payments.ex.view.helper.ValidationHelper;
import com.clip.payments.ex.view.helper.impl.ValidationHelperImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class ConversionHelperImpl implements ConversionHelper{

	public PaymentUser convertInput(String sInput) throws ValidationException {
		final ValidationHelper vHelper = new ValidationHelperImpl();								
		//needed variables
		Payment payment = null;
		String userOp;
		String userId;
		String op;
		String jsonStr = "";
		int userOpLastIndex;
		OperationsEnum operation;
		
		try {
			if (vHelper.validateEntry(sInput) && sInput.contains("{")) {//contains json
				jsonStr = sInput.substring(sInput.indexOf("{"), sInput.length());
				userOpLastIndex = sInput.indexOf("{");
				//set this json, just if it is an add operation
				payment = convertPayment(vHelper.validateJSON(jsonStr));
			} else {//correct length
				userOpLastIndex = sInput.length();
			}
			
			//separate user from operation
			userOp = sInput.substring(0, userOpLastIndex);
			userId = userOp.substring(0, userOp.indexOf(" ")).trim();
			op = userOp.substring(userOp.indexOf(" ")).trim();
			if(userId.length() < 1 || !vHelper.isNumericAndNotZero(userId) || op.length() < 1) {//validate usr and op length								
				throw new ValidationException();
			}
			
			//validate correct operation
			if(vHelper.validateUUID(op)) {
				//set the UIDD value
				operation = OperationsEnum.CONSULT;
				payment = new Payment();
				payment.setTransaction_id(UUID.fromString(op));
			} else {
				operation = vHelper.validateOperation(op);
				if(operation.equals(OperationsEnum.ADD) && jsonStr.equalsIgnoreCase("")) {
					throw new ValidationException();
				}
			}			

		}catch (StringIndexOutOfBoundsException ex) {
			throw new ValidationException();
		} catch (ParseException e) {
			throw new ValidationException("Check date format");
		} catch (JsonSyntaxException e) {
			throw new ValidationException("Check JSON format");
		}
	
		return new PaymentUser(userId, payment, null, null, operation);
	}

	
	
	public String objToJsonString(Object object) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(object);
	}
	
	
	
	private Payment convertPayment(JsonObject jsonObj) throws ParseException {
		Payment payment = new Payment();
		payment.setAmount(jsonObj.get("amount").getAsBigDecimal());
		payment.setDate(DateUtil.stringToDate(jsonObj.get("date").getAsString()));
		payment.setDescription(jsonObj.get("description").getAsString());
		return payment;
	}

}
