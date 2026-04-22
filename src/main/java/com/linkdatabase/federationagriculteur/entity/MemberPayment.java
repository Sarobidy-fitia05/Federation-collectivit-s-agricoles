package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class MemberPayment {
    private String id;
    private BigDecimal amount;
    private String membershipFeeId;
    private String financialAccountId;
    private PaymentMode paymentMode;
    private LocalDateTime creationDate = LocalDateTime.now();
    private String memberId;
}
