package com.clip.payments.ex.daos.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.io.input.ReversedLinesFileReader;

import com.clip.payments.ex.daos.PaymentUserDAO;
import com.clip.payments.ex.dtos.Payment;
import com.clip.payments.ex.dtos.PaymentUser;
import com.clip.payments.ex.exceptions.PaymentUserDAOException;
import com.clip.payments.ex.utils.DateUtil;
import com.clip.payments.ex.view.ReadConsole;

public class PaymentUserDAOImpl implements PaymentUserDAO {

	
	
	public void addPayment(final PaymentUser paymentUser) throws PaymentUserDAOException {
		
		try {
			String path = ReadConsole.getPath();
			String fileSeparator = System.getProperty("file.separator");
			String userFilePath = path + fileSeparator + "ClipPayments_DB" + fileSeparator + paymentUser.getUserId() + ".txt";
			
			File userFile = new File(userFilePath);
			// if user file doesn't exist, throw an exception
			if (!userFile.exists()) {
				if (userFile.createNewFile()) {
					System.out.println("New user file created: " + userFilePath);
				}
			}
			String line = createRegistry(paymentUser);
			writeFile(userFile, line);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			throw new PaymentUserDAOException(e.getMessage());
		} 	
	}


	
	public Payment showPayment(final PaymentUser paymentUser) throws PaymentUserDAOException {
		
		Payment payment = null;		
		try {
			String path = ReadConsole.getPath();
			String fileSeparator = System.getProperty("file.separator");
			String userFilePath = path + fileSeparator + "ClipPayments_DB" + fileSeparator + paymentUser.getUserId() + ".txt";
			
			File userFile = new File(userFilePath);
			// if user file doesn't exist, throw an exception
			if (!userFile.exists()) {
				throw new PaymentUserDAOException("Transaction not found");
			} else {
				//get specific record
				payment = getRecord(userFilePath, paymentUser.getUserId(), paymentUser.getPayment().getTransaction_id().toString());
				if (null == payment) {
					throw new PaymentUserDAOException("Transaction not found");
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			throw new PaymentUserDAOException();
		}
		return payment;
	}

	
	
	public TreeMap<String, Payment> listPayments(final PaymentUser paymentUser) throws PaymentUserDAOException {
		
		TreeMap<String, Payment> operationsSet = new TreeMap<String, Payment>();		
		try {
			String path = ReadConsole.getPath();
			String fileSeparator = System.getProperty("file.separator");
			String userFilePath = path + fileSeparator + "ClipPayments_DB" + fileSeparator + paymentUser.getUserId() + ".txt";
			
			File userFile = new File(userFilePath);
			// if user file doesn't exist, throw an exception
			if (!userFile.exists()) {
				throw new PaymentUserDAOException("User doesn't exist");
			// Do the correspoding operation
			} else {
				//get all records
				operationsSet = getUserRecords(userFilePath, paymentUser.getUserId());
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			throw new PaymentUserDAOException();
		}
		return operationsSet;
	}
	
	
	
	private String createRegistry(PaymentUser paymentUser) throws ParseException {
		StringBuffer sb = new StringBuffer();
		return sb.append(paymentUser.getPayment().getTransaction_id()).append("|").append(paymentUser.getPayment().getAmount().toPlainString())
		.append("|").append(paymentUser.getPayment().getDescription()).append("|").append(DateUtil.dateToString(paymentUser.getPayment().getDate())).toString();
	}
	
	
	
	private TreeMap<String, Payment> getUserRecords(final String userFilePath, final String userId) throws IOException, ParseException{
		
		TreeMap<String, Payment> operationsSet = new TreeMap<String, Payment>();
		//Read the file
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(userFilePath);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();		        
//		        System.out.println(line);
		        
				String[] tokens = line.split("\\|");
				operationsSet.put(tokens[0] ,new Payment(new BigDecimal(tokens[1]), tokens[2], 
						DateUtil.stringToDate(tokens[3]), UUID.fromString(tokens[0]), userId));
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		    return operationsSet;
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
	
	
	
	private Payment getRecord(final String userFilePath, final String userId, final String transactionId) throws IOException, ParseException{
		//Read the file
		FileInputStream inputStream = null;
		Scanner sc = null;
		Payment payment = null;
		try {
		    inputStream = new FileInputStream(userFilePath);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();		        
//		        System.out.println(line);
		        
				String[] tokens = line.split("\\|");
				if(tokens[0].equalsIgnoreCase(transactionId)) {
					payment = new Payment(new BigDecimal(tokens[1]), tokens[2], 
							DateUtil.stringToDate(tokens[3]), UUID.fromString(tokens[0]), userId);
					break;
				}
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		    return payment;
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
	
	
		
    private void writeFile(File file, String line) throws IOException{
    	FileWriter writer = null;
    	ReversedLinesFileReader reverseReader = null;
    	try{
            //Write Content
            writer = new FileWriter(file, true);

        	reverseReader = new ReversedLinesFileReader(file, Charset.forName("UTF-8"));
            String lastLine = reverseReader.readLine();
            
			if (null == lastLine || lastLine.trim().isEmpty() || lastLine.contains("\r\n")) {
				writer.write(line);
			} else {
				writer.write("\r\n");// write new line
				writer.write(line);
			}            		
        }catch(Exception e){
            e.printStackTrace();
        } finally {
        	if (null != reverseReader) {
    			reverseReader.close();        		
        	}
        	if (null != writer) {
        		writer.close();   
        	}
        }
    }

}
