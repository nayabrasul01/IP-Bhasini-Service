package com.esic.checklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esic.checklist.model.IpVitals;

public interface IpVitalsRepository extends JpaRepository<IpVitals, Long> {

	List<IpVitals> findByIpNumber(String ipNumber);
}

