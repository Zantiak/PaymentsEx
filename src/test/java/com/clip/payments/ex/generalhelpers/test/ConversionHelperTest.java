package com.clip.payments.ex.generalhelpers.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.clip.payments.ex.exceptions.ValidationException;
import com.clip.payments.ex.generalhelpers.ConversionHelper;
import com.clip.payments.ex.generalhelpers.impl.ConversionHelperImpl;
import com.clip.payments.ex.view.ViewTestConstants;

public class ConversionHelperTest {
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
	@Test
	public void testValidateOpEntry() throws ValidationException {
		ConversionHelper ch = new ConversionHelperImpl();
		ch.convertInput(ViewTestConstants.VALID_OPERATION_INPUT);		
	}
	
	@Test
	public void testValidateAddEntry() throws ValidationException {
		ConversionHelper ch = new ConversionHelperImpl();
		ch.convertInput(ViewTestConstants.VALID_ADD_INPUT);		
	}

	@Test
	public void testValidateEntryFail() throws ValidationException {
        thrown.expect(ValidationException.class);
        thrown.expectMessage("Incorrect Operation command");
		ConversionHelper ch = new ConversionHelperImpl();
		ch.convertInput(ViewTestConstants.INCORRECT_INPUT);		
	}
	
	@Test
	public void testValidateEntryInclomplete() throws ValidationException {
        thrown.expect(ValidationException.class);
        thrown.expectMessage("Incorrect parameters!");
		ConversionHelper ch = new ConversionHelperImpl();
		ch.convertInput(ViewTestConstants.INCOMPLETE_INPUT);		
	}
}
