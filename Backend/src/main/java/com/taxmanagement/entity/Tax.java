package com.taxmanagement.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "taxes")
public class Tax {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tax_form_id")
	private Long taxFormId;

	@Column(name = "form_type", nullable = false)
	private String formType;

	@Column(name = "filing_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date filingDate;

	@Column(name = "total_tax_amount", nullable = false)
	private BigDecimal totalTaxAmount;

	@Column(name = "user_id", nullable = false)
	private int userId;

	public Tax() {
		super();
	}

	public Tax(Long taxFormId, String formType, Date filingDate, BigDecimal totalTaxAmount, int userId) {
		super();
		this.taxFormId = taxFormId;
		this.formType = formType;
		this.filingDate = filingDate;
		this.totalTaxAmount = totalTaxAmount;
		this.userId = userId;
	}

	public Long getTaxFormId() {
		return taxFormId;
	}

	public void setTaxFormId(Long taxFormId) {
		this.taxFormId = taxFormId;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Tax [taxFormId=" + taxFormId + ", formType=" + formType + ", filingDate=" + filingDate
				+ ", totalTaxAmount=" + totalTaxAmount + ", userId=" + userId + "]";
	}
}
