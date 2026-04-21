package com.linkdatabase.federationagriculteur.entity;

import lombok.Data;
import java.util.List;

@Data
public class Collectivity {
    private String id;
    private String location;
    private CollectivityStructure structure;
    private List<Member> members;
}