package com.clip.payments.ex.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;

import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentOperationException;
import com.clip.payments.ex.exceptions.ValidationException;
import com.clip.payments.ex.generalhelpers.ConversionHelper;
import com.clip.payments.ex.generalhelpers.impl.ConversionHelperImpl;
import com.clip.payments.ex.services.PaymentOperationsDelegate;

public class ReadConsole {

	public static void main(String[] args) throws IOException {
		ReadConsole rc = new ReadConsole();
		
		//Create data directory
		rc.createDataDirectory();
		//At the start load the data
		rc.loadData();
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
//					System.out.println(cin.getEncoding());
					if(null == sInput || sInput.equalsIgnoreCase(ViewConstants.EMPTY_ENTRY)) {
						System.out.println("No input where received");
						System.out.print(ViewConstants.APP_ENTRY);
					} else if (exitFlag) {
						//Here goes the magic, convert the entry and inside the convertion validate the entry
						callOperations(sInput);
//						System.out.println(sInput);
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
		String path = getPath();

		String fileSeparator = System.getProperty("file.separator");
		String newDir = path + fileSeparator + "ClipPayments_DB" + fileSeparator;
//		JOptionPane.showMessageDialog(null, newDir);// TODO remove this line

		File file = new File(newDir);
		file.mkdir();
	}
	
	private void loadData() throws UnsupportedEncodingException {
		//move data in resources folder to application folder
		String path = getPath();
		String fileSeparator = System.getProperty("file.separator");
		
		ClassLoader classLoader = getClass().getClassLoader();
		
		File source = new File(classLoader.getResource("ClipPayments_DB").getPath());
		File dest = new File(path + fileSeparator + "ClipPayments_DB" + fileSeparator);
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static String getPath() throws UnsupportedEncodingException {
		URL url = ReadConsole.class.getProtectionDomain().getCodeSource().getLocation();
		String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
		String parentPath = new File(jarPath).getParentFile().getPath();
//		String parentPath2 = new File(parentPath).getParentFile().getPath();
		return parentPath;
	}
	
}
