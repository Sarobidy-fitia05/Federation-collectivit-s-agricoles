package com.linkdatabase.federationagriculteur.entity;

import lombok.NoArgsConstructor;
 import java.time.LocalDate;

@data
@NoArgsConstructor
public class MembershipFee {
    private String id;
    private LocalDate elligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;
    private ActivityStatus status;
    private String collectivityId;
}
