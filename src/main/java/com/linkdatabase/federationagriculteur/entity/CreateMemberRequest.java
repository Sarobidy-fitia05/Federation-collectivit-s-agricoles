package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateMemberRequest {
    // MemberInformation
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;

    // CreateMember specific
    private String collectivityIdentifier;
    private List<String> referees;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
}