package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Member extends MemberInformation {
    private String id;
    private List<Member> referees;
}