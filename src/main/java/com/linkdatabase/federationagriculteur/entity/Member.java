package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode
public class Member extends MemberInformation {
    private String id;
    private List<Member> referees;
}