package edu.hei.school.agricultural.entity;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberPayment {
    private String id;
    private Double amount;
    private LocalDate creationDate;
    private Member member;
    private MembershipFee membershipFee;
}
