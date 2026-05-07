package edu.hei.school.agricultural.controller.dto;

import edu.hei.school.agricultural.entity.ActivityType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityActivity {
    private String label;
    private ActivityType activityType;
    private List<MemberOccupation> memberOccupationConcerned;
    private MonthlyRecurrenceRule recurrenceRule;
    private LocalDate executiveDate;
}