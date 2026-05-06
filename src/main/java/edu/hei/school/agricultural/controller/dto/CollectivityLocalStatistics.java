package edu.hei.school.agricultural.controller.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityLocalStatistics {
    private String memberId;
    private String firstName;
    private String lastName;
    private Double earnedAmount;
    private Double unpaidAmount;


}
