package com.linkdatabase.federationagriculteur.service;

import com.linkdatabase.federationagriculteur.dto.CreateMemberPayment;
import com.linkdatabase.federationagriculteur.entity.MemberPayment;
import com.linkdatabase.federationagriculteur.exception.ConflictException;
import com.linkdatabase.federationagriculteur.repository.MemberPaymentRepository;

public class MemberPaymentService {
    private MemberPaymentRepository memberPaymentRepository;

    public MemberPaymentService(MemberPaymentRepository memberPaymentRepository) {
        this.memberPaymentRepository = memberPaymentRepository;
    }

    public MemberPayment createPayment (CreateMemberPayment request)
    {
        if(request.getId() == null || request.getId().isBlank()) {
            throw new IllegalArgumentException("Payment id is required");
        }

        if(memberPaymentRepository.existsById(request.getId())) {
            throw new ConflictException(
                    "Payment with id '" + request.getId() + "' already exists"
            );
        }

        if(request.getAmount() == null || request.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Amount must be strictly positive");
        }

        if(request.getMemberId() == null || request.getMemberId().isBlank()) {
            throw new IllegalArgumentException("Member id is required");
        }

        MemberPayment payment = new MemberPayment();
        payment.setId(request.getId());
        payment.setAmount(request.getAmount());
        payment.setMembershipFeeId(request.getMembershipFeeId());
        payment.setFinancialAccountId(request.getFinancialAccountId());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setMemberId(request.getMemberId());

        return memberPaymentRepository.insert(payment);
    }
}
