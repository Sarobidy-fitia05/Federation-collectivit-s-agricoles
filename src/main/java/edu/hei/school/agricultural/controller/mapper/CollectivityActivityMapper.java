package edu.hei.school.agricultural.mapper;

import edu.hei.school.agricultural.entity.ActivityType;
import edu.hei.school.agricultural.entity.CollectivityActivity;
import edu.hei.school.agricultural.entity.MemberOccupation;
import edu.hei.school.agricultural.entity.MonthlyRecurrenceRule;
import edu.hei.school.agricultural.repository.CollectivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CollectivityActivityMapper {

    private final CollectivityRepository collectivityRepository;

    public CollectivityActivity mapFromResultSet(ResultSet rs) throws SQLException {
        // Chargement de la collectivité propriétaire
        var collectivity = collectivityRepository.findById(rs.getString("collectivity_id")).orElse(null);

        // Récupération des occupations concernées (tableau PostgreSQL)
        List<MemberOccupation> occupations = new ArrayList<>();
        Array arr = rs.getArray("member_occupations");
        if (arr != null) {
            String[] occ = (String[]) arr.getArray();
            for (String s : occ) {
                occupations.add(MemberOccupation.valueOf(s));
            }
        }

        return CollectivityActivity.builder()
                .id(rs.getString("id"))
                .collectivity(collectivity)
                .label(rs.getString("label"))
                .activityType(ActivityType.valueOf(rs.getString("activity_type")))
                .executiveDate(rs.getDate("executive_date") != null
                        ? rs.getDate("executive_date").toLocalDate() : null)
                .recurrenceRule(mapRecurrenceRule(rs))
                .memberOccupationConcerned(occupations)
                .build();
    }

    private MonthlyRecurrenceRule mapRecurrenceRule(ResultSet rs) throws SQLException {
        Integer weekOrdinal = rs.getObject("recurrence_week_ordinal", Integer.class);
        String dayOfWeek = rs.getString("recurrence_day_of_week");
        if (weekOrdinal == null || dayOfWeek == null) return null;
        return new MonthlyRecurrenceRule(weekOrdinal, dayOfWeek);
    }
}