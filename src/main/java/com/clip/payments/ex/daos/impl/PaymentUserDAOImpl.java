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
import com.clip.payments.ex.utils.Constants;
import com.clip.payments.ex.utils.DateUtil;
import com.clip.payments.ex.utils.Util;

public class PaymentUserDAOImpl implements PaymentUserDAO {

	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	private static String BD_DIR_NAME = "ClipPayments_DB";
	private static String PIPE_CHAR = "|";
	private static String ESCAPE_PIPE_CHAR = "\\|";
	private static String NEW_LINE = "\r\n";
	private static String TXT_FILE_EXTENSION = ".txt";
	
	
	
	public void addPayment(final PaymentUser paymentUser) throws PaymentUserDAOException {
		
		try {
			File userFile = getUserFile (paymentUser.getUserId());
			// if user file doesn't exist, throw an exception
			if (!userFile.exists()) {
				if (userFile.createNewFile()) {
					System.out.println("New user file created: " + userFile.getAbsolutePath());
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
			File userFile = getUserFile (paymentUser.getUserId());	
			// if user file doesn't exist, throw an exception
			if (!userFile.exists()) {
				throw new PaymentUserDAOException("Transaction not found");
			} else {
				//get specific record
				payment = getRecord(userFile.getAbsolutePath(), paymentUser.getUserId(),
						paymentUser.getPayment().getTransaction_id().toString());
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
			File userFile = getUserFile (paymentUser.getUserId());	
			if (!userFile.exists()) {
				// if user file doesn't exist, throw an exception
				throw new PaymentUserDAOException("User doesn't exist");
			} else {
				//get all records
				operationsSet = getUserRecords(userFile.getAbsolutePath(), paymentUser.getUserId());
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			throw new PaymentUserDAOException();
		}
		return operationsSet;
	}
	
	
	
	private String createRegistry(PaymentUser paymentUser) throws ParseException {
		StringBuffer sb = new StringBuffer();
		return sb.append(paymentUser.getPayment().getTransaction_id()).append(PIPE_CHAR)
				.append(paymentUser.getPayment().getAmount().toPlainString()).append(PIPE_CHAR)
				.append(paymentUser.getPayment().getDescription()).append(PIPE_CHAR)
				.append(DateUtil.dateToString(paymentUser.getPayment().getDate())).toString();
	}
	
	
	
	private TreeMap<String, Payment> getUserRecords(final String userFilePath, final String userId) throws IOException, ParseException{
		
		TreeMap<String, Payment> operationsSet = new TreeMap<String, Payment>();
		//Read the file
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(userFilePath);
		    sc = new Scanner(inputStream, Constants.CHAR_ENCODING);
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();		        		        
				String[] tokens = line.split(ESCAPE_PIPE_CHAR);
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
		    sc = new Scanner(inputStream, Constants.CHAR_ENCODING);
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();		        
		        
				String[] tokens = line.split(ESCAPE_PIPE_CHAR);
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

        	reverseReader = new ReversedLinesFileReader(file, Charset.forName(Constants.CHAR_ENCODING));
            String lastLine = reverseReader.readLine();
            
			if (null == lastLine || lastLine.trim().isEmpty() || lastLine.contains(NEW_LINE)) {
				writer.write(line);
			} else {
				writer.write(NEW_LINE);// write new line
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
    
    
    
    private File getUserFile (String userId) throws UnsupportedEncodingException {
		String path = Util.getPath();
		String userFilePath = path + FILE_SEPARATOR + BD_DIR_NAME + FILE_SEPARATOR + userId + TXT_FILE_EXTENSION;

		return new File(userFilePath);
    }

}
