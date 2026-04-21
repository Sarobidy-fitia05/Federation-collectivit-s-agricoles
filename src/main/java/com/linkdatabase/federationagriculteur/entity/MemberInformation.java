package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MemberInformation {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;
}