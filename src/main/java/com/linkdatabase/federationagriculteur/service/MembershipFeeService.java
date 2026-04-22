package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.entity.MembershipFee;
import com.linkdatabase.federationagriculteur.repository.CollectivityRepository;
import com.linkdatabase.federationagriculteur.repository.MembershipFeeRepository;

import java.util.List;

public class MembershipFeeService {
    private final MembershipFeeRepository membershipFeeRepository;
    private final CollectivityRepository collectivityRepository;

     public MembershipFeeService(MembershipFeeRepository membershipFeeRepository,
                                 CollectivityRepository collectivityRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembershipFee> getMembershipFees(String collectivityId) {
         collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new RuntimeException("Collectivity not found with id: " + collectivityId));

         return membershipFeeRepository.findByCollectivityId(collectivityId);
    }
}
