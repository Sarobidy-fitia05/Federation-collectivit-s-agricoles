package com.linkdatabase.federationagriculteur.dto;

import com.linkdatabase.federationagriculteur.entity.PaymentMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class CreateMemberPayment {
    private String id;
    private BigDecimal amount;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private PaymentMode paymentMode;
}
