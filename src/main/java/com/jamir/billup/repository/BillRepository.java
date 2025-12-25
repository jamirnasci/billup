package com.jamir.billup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jamir.billup.model.Bill;
import com.jamir.billup.model.BillStatus;

public interface BillRepository extends JpaRepository<Bill, Long>{
	public List<Bill> findByStatus(BillStatus status);
}
