package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.dto.CreateMemberPayment;
import com.linkdatabase.federationagriculteur.entity.MemberPayment;
import com.linkdatabase.federationagriculteur.exception.ConflictException;
import com.linkdatabase.federationagriculteur.repository.MemberPaymentRepository;

public class MemberPaymentService {

    private final MemberPaymentRepository memberPaymentRepository;

    public MemberPaymentService(MemberPaymentRepository memberPaymentRepository) {
        this.memberPaymentRepository = memberPaymentRepository;
    }

    public MemberPayment createPayment(CreateMemberPayment request) {
        if (request.getId() == null || request.getId().isBlank()) {
            throw new IllegalArgumentException("Payment id is required");
        }

        if (memberPaymentRepository.existsById(request.getId())) {
            throw new ConflictException(
                    "Payment with id '" + request.getId() + "' already exists"
            );
        }

        MemberPayment payment = new MemberPayment();
        payment.setId(request.getId());
        payment.setAmount(request.getAmount());
        payment.setMembershipFeeId(request.getMembershipFeeIdentifier());
        payment.setFinancialAccountId(request.getAccountCreditedIdentifier());
        payment.setPaymentMode(request.getPaymentMode());

        return memberPaymentRepository.insert(payment);
    }
}