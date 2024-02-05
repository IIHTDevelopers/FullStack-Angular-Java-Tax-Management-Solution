package com.taxmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxmanagement.entity.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
}