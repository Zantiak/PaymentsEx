package com.clip.payments.ex.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.exceptions.ValidationException;
import com.clip.payments.ex.generalhelpers.ConversionHelper;
import com.clip.payments.ex.generalhelpers.impl.ConversionHelperImpl;
import com.clip.payments.ex.services.PaymentOperationsDelegate;
import com.clip.payments.ex.utils.Util;

public class ReadConsole {
	
	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	private static String BD_DIR_NAME = "ClipPayments_DB";

	public static void main(String[] args) throws IOException {
		ReadConsole rc = new ReadConsole();
		
		//Create data directory
		rc.createDataDirectory();
		rc.readInput(new InputStreamReader(System.in));				
	}
	
	
	
	public void readInput(InputStreamReader systemIn) {
		try {
			
			BufferedReader br = new BufferedReader(systemIn);
			System.out.println("Enter an operation, 'q' to quit.");
			System.out.print(ViewConstants.APP_ENTRY);
			String sInput = null;
			boolean exitFlag = true;
			do {
				try {
					sInput = br.readLine().trim();
					exitFlag = !sInput.equals(ViewConstants.EXIT_KEY);
					if(null == sInput || sInput.equalsIgnoreCase(ViewConstants.EMPTY_ENTRY)) {
						System.out.println("No input where received");
						System.out.print(ViewConstants.APP_ENTRY);
					} else if (exitFlag) {
						//Here goes the magic, convert the entry and inside the conversion validate the entry
						callOperations(sInput);
						System.out.print(ViewConstants.APP_ENTRY);
					} else {
						System.out.println("Seeeeya");
					}
				} catch (ValidationException | PaymentOperationException e) {
					System.out.println(e.getMessage());
					System.out.print(ViewConstants.APP_ENTRY);
				} 
			} while (exitFlag);
		} catch (IOException ioe){
			System.out.println("ERROR: An error occur " + ioe.getMessage());
		}
	}
	
	
	
	private void callOperations(String sInput) throws PaymentOperationException, ValidationException {
		PaymentOperationsDelegate pod = new PaymentOperationsDelegate();
		ConversionHelper ch = new ConversionHelperImpl();
		PrintConsole pc = new PrintConsole();

		PaymentUser paymentUser = pod.doOperation(ch.convertInput(sInput));
		
		switch(paymentUser.getOperationEnum()) {
			case ADD:
				pc.printAdd(paymentUser);
				break;
			case CONSULT:
				pc.printConsult(paymentUser);
				break;
			case LIST:
				pc.printList(paymentUser);
				break;
			case SUM:
				pc.printSum(paymentUser);
				break;
		}
	}
	
	
	
	private void createDataDirectory() throws UnsupportedEncodingException {		
		String path = Util.getPath();
		String newDir = path + FILE_SEPARATOR + BD_DIR_NAME + FILE_SEPARATOR;

		File file = new File(newDir);
		file.mkdir();
	}
	
}
