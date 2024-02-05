package com.taxmanagement.exception;

import static com.taxmanagement.utils.MasterData.getTaxDTO;
import static com.taxmanagement.utils.TestUtils.currentTest;
import static com.taxmanagement.utils.TestUtils.exceptionTestFile;
import static com.taxmanagement.utils.TestUtils.testReport;
import static com.taxmanagement.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.taxmanagement.controller.TaxController;
import com.taxmanagement.dto.TaxDTO;
import com.taxmanagement.service.TaxService;
import com.taxmanagement.utils.MasterData;

@WebMvcTest(TaxController.class)
@AutoConfigureMockMvc
public class TaxExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaxService taxService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateTaxInvalidDataException() throws Exception {
		TaxDTO taxDTO = getTaxDTO();
		taxDTO.setFilingDate(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/taxes")
				.content(MasterData.asJsonString(taxDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateTaxInvalidDataException() throws Exception {
		TaxDTO taxDTO = getTaxDTO();
		taxDTO.setFormType(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/taxes/" + taxDTO.getTaxFormId())
				.content(MasterData.asJsonString(taxDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetTaxByIdResourceNotFoundException() throws Exception {
		TaxDTO taxDTO = getTaxDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Tax not found");

		when(this.taxService.getTaxById(taxDTO.getTaxFormId()))
				.thenThrow(new ResourceNotFoundException("Tax not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/taxes/" + taxDTO.getTaxFormId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testDeleteTaxByIdResourceNotFoundException() throws Exception {
		TaxDTO taxDTO = getTaxDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Tax not found");

		when(this.taxService.deleteTaxById(taxDTO.getTaxFormId()))
				.thenThrow(new ResourceNotFoundException("Tax not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/taxes/" + taxDTO.getTaxFormId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}
