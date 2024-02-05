package com.taxmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taxmanagement.dto.TaxDTO;
import com.taxmanagement.service.TaxService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/taxes")
public class TaxController {

	private final TaxService taxService;

	@Autowired
	public TaxController(TaxService taxService) {
		this.taxService = taxService;
	}

	@PostMapping
	public ResponseEntity<TaxDTO> createTax(@RequestBody @Valid TaxDTO model) {
		TaxDTO result = taxService.createTax(model);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaxDTO> updateTax(@RequestBody @Valid TaxDTO model) {
		TaxDTO existingTax = taxService.updateTax(model);
		if (existingTax == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(existingTax, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTax(@PathVariable long id) {
		boolean result = taxService.deleteTaxById(id);
		if (!result) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaxDTO> getTaxById(@PathVariable long id) {
		TaxDTO tax = taxService.getTaxById(id);
		return new ResponseEntity<>(tax, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<TaxDTO>> getAllTaxes() {
		List<TaxDTO> taxes = taxService.getAllTaxes();
		return new ResponseEntity<>(taxes, HttpStatus.OK);
	}
}
