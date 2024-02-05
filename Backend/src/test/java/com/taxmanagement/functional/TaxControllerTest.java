package com.taxmanagement.functional;

import static com.taxmanagement.utils.MasterData.asJsonString;
import static com.taxmanagement.utils.MasterData.getTaxDTO;
import static com.taxmanagement.utils.TestUtils.businessTestFile;
import static com.taxmanagement.utils.TestUtils.currentTest;
import static com.taxmanagement.utils.TestUtils.testReport;
import static com.taxmanagement.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.taxmanagement.controller.TaxController;
import com.taxmanagement.dto.TaxDTO;
import com.taxmanagement.service.TaxService;

@WebMvcTest(TaxController.class)
@AutoConfigureMockMvc
public class TaxControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaxService taxService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateTax() throws Exception {
		TaxDTO taxDTO = getTaxDTO();

		when(this.taxService.createTax(any())).thenReturn(taxDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/taxes").content(asJsonString(taxDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(taxDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetTaxById() throws Exception {
		TaxDTO taxDTO = getTaxDTO();

		when(this.taxService.getTaxById(any())).thenReturn(taxDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/taxes/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(taxDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateTax() throws Exception {
		TaxDTO taxDTO = getTaxDTO();
		when(this.taxService.updateTax(any())).thenReturn(taxDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/taxes/1").content(asJsonString(taxDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(taxDTO)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteTax() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/taxes/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetAllTaxes() throws Exception {
		TaxDTO taxDTO1 = getTaxDTO();
		TaxDTO taxDTO2 = getTaxDTO();
		when(this.taxService.getAllTaxes()).thenReturn(List.of(taxDTO1, taxDTO2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/taxes").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(List.of(taxDTO1, taxDTO2)))
						? "true"
						: "false"),
				businessTestFile);
	}
}
