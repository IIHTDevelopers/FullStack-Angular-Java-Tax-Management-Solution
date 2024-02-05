package com.taxmanagement.service;

import java.util.List;

import com.taxmanagement.dto.TaxDTO;

public interface TaxService {
	TaxDTO createTax(TaxDTO taxDTO);

	boolean deleteTaxById(Long id);

	List<TaxDTO> getAllTaxes();

	TaxDTO getTaxById(Long id);

	TaxDTO updateTax(TaxDTO taxDTO);
}
