package com.svpoc.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svpoc.entity.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
	
	public List<Balance> findByAccountId(Integer accountId);
}
