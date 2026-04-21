package com.linkdatabase.federationagriculteur.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Member {
    private Long id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String job;
    private String phone;
    private String email;
    private LocalDate membershipDate;
    private MemberOccupation memberOccupation;
    //collectivity id implemented in the future

}
//original members
