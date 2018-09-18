package com.clip.payments.ex.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.generalhelpers.ConversionHelper;
import com.clip.payments.ex.generalhelpers.impl.ConversionHelperImpl;

public class PrintConsole {

	final ConversionHelper ch = new ConversionHelperImpl(); 
	
	public void printAdd(final PaymentUser paymentUser) {
		System.out.println(ch.objToJsonString(paymentUser.getPayment()));
	}
	
	
	
	public void printConsult(final PaymentUser paymentUser) {
		System.out.println(ch.objToJsonString(paymentUser.getPayment()));
	}
	
	
	
	public void printList(final PaymentUser paymentUser) {
		System.out.println(ch.objToJsonString(paymentUser.getPayments()).replaceAll("\\[", "\\[\n\n")
				.replaceAll("}]", "}\n\n]").replaceAll("},", "},\n\n"));
	}
	
	
	
	public void printSum(final PaymentUser paymentUser) {
		StringBuilder sb = new StringBuilder();
		BigDecimal bd = paymentUser.getPaymentsSum().setScale(2, BigDecimal.ROUND_DOWN);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		String result = df.format(bd);
		
		System.out.println(sb.append("{\"user_id\":").append(paymentUser.getUserId()).append(",\"sum\":").append(result).append("}").toString());
	}

}
