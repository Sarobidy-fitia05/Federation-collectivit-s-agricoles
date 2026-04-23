package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FinancialAccount {
    private String id;
    private BigDecimal amount;
    private String collectivityId;
    private FinancialAccountType type;
}