package com.esic.checklist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esic.checklist.model.IpDetails;

public interface IpDetailsRepository extends JpaRepository<IpDetails, Long> {
    Optional<IpDetails> findByIpNumber(String ipNumber);
}