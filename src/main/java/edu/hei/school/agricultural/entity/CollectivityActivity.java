package edu.hei.school.agricultural.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityActivity {
    private String id;
    private Collectivity collectivity;
    private String label;
    private ActivityType activityType;
    private List<MemberOccupation> memberOccupationConcerned;
    private LocalDate executiveDate;
    private MonthlyRecurrenceRule recurrenceRule;
}