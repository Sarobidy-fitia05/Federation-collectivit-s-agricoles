package com.linkdatabase.federationagriculteur.dto;

import com.linkdatabase.federationagriculteur.entity.MemberInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateMemberRequest extends MemberInformation {

    private String collectivityIdentifier;
    private List<String> referees;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
}