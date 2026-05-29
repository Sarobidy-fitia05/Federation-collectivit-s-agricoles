package edu.hei.school.agricultural.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityLocalStatistics {
    private String memberId;
    private String firstName;
    private String lastName;
    private double earnedAmount;
    private double unpaidAmount;
    private double assiduityPercentage; // NEW
}