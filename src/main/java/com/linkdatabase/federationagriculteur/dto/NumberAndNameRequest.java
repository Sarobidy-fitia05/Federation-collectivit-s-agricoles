package com.linkdatabase.federationagriculteur.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NumberAndNameRequest {
    private String number;
    private String name;
}