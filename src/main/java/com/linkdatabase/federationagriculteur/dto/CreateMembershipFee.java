package com.linkdatabase.federationagriculteur.dto;

import com.linkdatabase.federationagriculteur.entity.Frequency;
import java.time.LocalDate;
@data
public class CreateMembershipFee {
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;
}
