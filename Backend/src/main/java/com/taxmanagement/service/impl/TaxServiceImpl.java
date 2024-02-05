package com.taxmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxmanagement.dto.TaxDTO;
import com.taxmanagement.entity.Tax;
import com.taxmanagement.exception.ResourceNotFoundException;
import com.taxmanagement.repo.TaxRepository;
import com.taxmanagement.service.TaxService;

@Service
public class TaxServiceImpl implements TaxService {

	private final TaxRepository taxRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public TaxServiceImpl(TaxRepository taxRepository, ModelMapper modelMapper) {
		this.taxRepository = taxRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public TaxDTO createTax(TaxDTO taxDTO) {
		Tax tax = modelMapper.map(taxDTO, Tax.class);
		Tax savedTax = taxRepository.save(tax);
		return modelMapper.map(savedTax, TaxDTO.class);
	}

	@Override
	public boolean deleteTaxById(Long id) {
		if (taxRepository.existsById(id)) {
			taxRepository.deleteById(id);
			return true;
		}
		throw new ResourceNotFoundException("Tax not found");
	}

	@Override
	public List<TaxDTO> getAllTaxes() {
		List<Tax> taxes = taxRepository.findAll();
		return taxes.stream().map(tax -> modelMapper.map(tax, TaxDTO.class)).collect(Collectors.toList());
	}

	@Override
	public TaxDTO getTaxById(Long id) {
		return taxRepository.findById(id).map(tax -> modelMapper.map(tax, TaxDTO.class))
				.orElseThrow(() -> new ResourceNotFoundException("Tax not found"));
	}

	@Override
	public TaxDTO updateTax(TaxDTO taxDTO) {
		Long taxId = taxDTO.getTaxFormId();
		if (taxRepository.existsById(taxId)) {
			Tax tax = modelMapper.map(taxDTO, Tax.class);
			Tax updatedTax = taxRepository.save(tax);
			return modelMapper.map(updatedTax, TaxDTO.class);
		}
		throw new ResourceNotFoundException("Tax not found");
	}
}
