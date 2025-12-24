package com.jamir.billup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jamir.billup.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
	public List<Client> findByUserId(Long userId);
}
