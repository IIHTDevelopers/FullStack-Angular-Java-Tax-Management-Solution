package com.taxmanagement.boundary;

import static com.taxmanagement.utils.TestUtils.boundaryTestFile;
import static com.taxmanagement.utils.TestUtils.currentTest;
import static com.taxmanagement.utils.TestUtils.testReport;
import static com.taxmanagement.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.taxmanagement.dto.TaxDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TaxBoundaryTest {

	private static Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@After
	public void afterAll() {
		testReport();
	}

	@Test
	public void testFormTypeNotBlank() throws IOException {
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setFormType(null);
		Set<ConstraintViolation<TaxDTO>> violations = validator.validate(taxDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testFilingDateNotNull() throws IOException {
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setFilingDate(null);
		Set<ConstraintViolation<TaxDTO>> violations = validator.validate(taxDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testFilingDatePastOrPresent() throws IOException {
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setFilingDate(new Date(System.currentTimeMillis() + 100000)); // Future date
		Set<ConstraintViolation<TaxDTO>> violations = validator.validate(taxDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testTotalTaxAmountNotNull() throws IOException {
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setTotalTaxAmount(null);
		Set<ConstraintViolation<TaxDTO>> violations = validator.validate(taxDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testTotalTaxAmountPositive() throws IOException {
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setTotalTaxAmount(BigDecimal.valueOf(-100)); // Negative value
		Set<ConstraintViolation<TaxDTO>> violations = validator.validate(taxDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
