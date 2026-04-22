package com.linkdatabase.federationagriculteur.dto;

import com.linkdatabase.federationagriculteur.entity.MemberInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateCollectivityRequest  {
    private String id;
    private String location;
    private List<String> members;
    private Boolean federationApproval;
    private CreateCollectivityStructure structure;
}