package edu.hei.school.agricultural.controller.mapper;

import edu.hei.school.agricultural.controller.dto.CreateCollectivityActivity;
import edu.hei.school.agricultural.controller.dto.MemberOccupation;
import edu.hei.school.agricultural.entity.ActivityType;
import edu.hei.school.agricultural.entity.CollectivityActivity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CollectivityActivityDtoMapper {

    public edu.hei.school.agricultural.controller.dto.CollectivityActivity mapToDto(CollectivityActivity entity) {
        edu.hei.school.agricultural.controller.dto.MonthlyRecurrenceRule dtoRule = null;
        if (entity.getRecurrenceRule() != null) {
            dtoRule = edu.hei.school.agricultural.controller.dto.MonthlyRecurrenceRule.builder()
                    .weekOrdinal(entity.getRecurrenceRule().getWeekOrdinal())
                    .dayOfWeek(entity.getRecurrenceRule().getDayOfWeek())
                    .build();
        }

        return edu.hei.school.agricultural.controller.dto.CollectivityActivity.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .activityType(ActivityType.valueOf(entity.getActivityType().name()))
                .memberOccupationConcerned(
                        entity.getMemberOccupationConcerned().stream()
                                .map(occ -> MemberOccupation.valueOf(occ.name()))
                                .collect(Collectors.toList()))
                .executiveDate(entity.getExecutiveDate())
                .recurrenceRule(dtoRule)
                .build();
    }

    public CollectivityActivity mapToEntity(CreateCollectivityActivity dto) {
        edu.hei.school.agricultural.entity.MonthlyRecurrenceRule rule = null;
        if (dto.getRecurrenceRule() != null) {
            rule = edu.hei.school.agricultural.entity.MonthlyRecurrenceRule.builder()
                    .weekOrdinal(dto.getRecurrenceRule().getWeekOrdinal())
                    .dayOfWeek(dto.getRecurrenceRule().getDayOfWeek())
                    .build();
        }

        return CollectivityActivity.builder()
                .label(dto.getLabel())
                .activityType(edu.hei.school.agricultural.entity.ActivityType.valueOf(dto.getActivityType().name()))
                .memberOccupationConcerned(
                        dto.getMemberOccupationConcerned().stream()
                                .map(occ -> edu.hei.school.agricultural.entity.MemberOccupation.valueOf(occ.name()))
                                .collect(Collectors.toList()))
                .executiveDate(dto.getExecutiveDate())
                .recurrenceRule(rule)
                .build();
    }
}