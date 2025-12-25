package com.jamir.billup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jamir.billup.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{

}
