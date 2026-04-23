package com.linkdatabase.federationagriculteur.dto;

import com.linkdatabase.federationagriculteur.entity.Frequency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class CreateMembershipFee {
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private String name;
    private BigDecimal amount;
    private String label;
}