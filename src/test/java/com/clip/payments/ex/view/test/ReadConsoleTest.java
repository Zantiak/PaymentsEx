package com.clip.payments.ex.view.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.clip.payments.ex.view.ReadConsole;


public class ReadConsoleTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	}

	@Test
	public void testReadInput() throws IOException {
		ReadConsole rc = new ReadConsole();
        byte[] data = "hello\nq".getBytes();
        
        InputStream input = new ByteArrayInputStream(data);
        InputStreamReader isr = new InputStreamReader(input);
        
        rc.readInput(isr);

        assertEquals("./application hello\n" + 
        		"./application Seeeeya\n", outContent.toString().substring(33));

//        assertEquals("./application Seeeeya\n" + 
//        		"", outContent.toString().substring(33));
	}
	
	@Test
	public void testReadInputBlank() throws IOException {
		ReadConsole rc = new ReadConsole();
        byte[] data = "\nq".getBytes();
        
        InputStream input = new ByteArrayInputStream(data);
        InputStreamReader isr = new InputStreamReader(input);
        
        rc.readInput(isr);

        assertEquals("./application No input where received\n" + 
        		"./application Seeeeya\n", outContent.toString().substring(33));
	}
	
//	@Test
//	public void testReadInputNull() throws IOException {
//		ReadConsole rc = new ReadConsole();
//        byte[] data = "\nq".getBytes();
//        
//        InputStream input = new ByteArrayInputStream(data);
//        InputStreamReader isr = null;
//        
//        rc.readInput(isr);
//
//        assertEquals("./application No input where received\n" + 
//        		"./application Seeeeya\n", outContent.toString().substring(33));
//	}

}
