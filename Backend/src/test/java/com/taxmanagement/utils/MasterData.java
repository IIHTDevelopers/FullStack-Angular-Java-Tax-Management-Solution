package com.taxmanagement.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.taxmanagement.dto.TaxDTO;

public class MasterData {

	public static TaxDTO getTaxDTO() {
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setTaxFormId(1L);
		taxDTO.setFormType("Income Tax");
		taxDTO.setFilingDate(new Date());
		taxDTO.setTotalTaxAmount(new BigDecimal("1000.00"));
		taxDTO.setUserId(123);
		return taxDTO;
	}

	public static List<TaxDTO> getTaxDTOList() {
		List<TaxDTO> taxDTOList = new ArrayList<>();
		TaxDTO taxDTO = new TaxDTO();
		taxDTO.setTaxFormId(1L);
		taxDTO.setFormType("Income Tax");
		taxDTO.setFilingDate(new Date());
		taxDTO.setTotalTaxAmount(new BigDecimal("1000.00"));
		taxDTO.setUserId(123);
		taxDTOList.add(taxDTO);
		return taxDTOList;
	}

	public static String asJsonString(Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule()); // Register the module to handle Java 8 date/time types
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
