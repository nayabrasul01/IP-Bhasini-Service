package com.esic.checklist.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esic.checklist.model.IpDetails;
import com.esic.checklist.repository.IpDetailsRepository;

@Service
public class IpDetailsService {
	
	private final IpDetailsRepository repository;

    public IpDetailsService(IpDetailsRepository repository) {
        this.repository = repository;
    }

    public Optional<IpDetails> getByIpNumber(String ipNumber) {
        return repository.findByIpNumber(ipNumber);
    }

}