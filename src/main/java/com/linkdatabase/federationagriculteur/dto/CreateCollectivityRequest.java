package com.linkdatabase.federationagriculteur.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateCollectivityRequest {
    private String location;
    private List<String> members;
    private Boolean federationApproval;
    private CreateCollectivityStructure structure;
}