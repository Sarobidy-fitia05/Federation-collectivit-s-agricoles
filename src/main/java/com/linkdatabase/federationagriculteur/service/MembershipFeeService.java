package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.dto.CreateMembershipFee;
import com.linkdatabase.federationagriculteur.entity.MembershipFee;
import com.linkdatabase.federationagriculteur.repository.CollectivityRepository;
import com.linkdatabase.federationagriculteur.repository.MembershipFeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<MembershipFee> createMembershipFees(String collectivityId, List<CreateMembershipFee> requests) {
         collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new RuntimeException("Collectivity not found with id: " + collectivityId));

        List<MembershipFee> createdFees = new ArrayList<>();

         for (CreateMembershipFee request : requests) {
             validateCreateRequest(request);

             if (membershipFeeRepository.existsByCollectivityIdAndLabel(collectivityId, request.getLabel())) {
                throw new RuntimeException("A membership fee with label '" + request.getLabel() + "' already exists for this collectivity");
            }

             MembershipFee fee = new MembershipFee();
            fee.setId(UUID.randomUUID().toString());
            fee.setEligibleFrom(request.getEligibleFrom());
            fee.setFrequency(request.getFrequency());
            fee.setAmount(request.getAmount());
            fee.setLabel(request.getLabel());
            fee.setStatus(ActivityStatus.ACTIVE);

             MembershipFee insertedFee = membershipFeeRepository.insert(fee, collectivityId);
            createdFees.add(insertedFee);
        }

        return createdFees;
    }


    private void validateCreateRequest(CreateMembershipFee request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be strictly positive");
        }
        if (request.getEligibleFrom() == null) {
            throw new IllegalArgumentException("EligibleFrom date is required");
        }
        if (request.getFrequency() == null) {
            throw new IllegalArgumentException("Frequency is required");
        }
        if (request.getLabel() == null || request.getLabel().isBlank()) {
            throw new IllegalArgumentException("Label is required and cannot be blank");
        }
    }
}
