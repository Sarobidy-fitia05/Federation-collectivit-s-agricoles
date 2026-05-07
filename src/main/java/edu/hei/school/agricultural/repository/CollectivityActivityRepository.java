package edu.hei.school.agricultural.repository;

import edu.hei.school.agricultural.datasource.DataSource;
import edu.hei.school.agricultural.entity.CollectivityActivity;
import edu.hei.school.agricultural.mapper.CollectivityActivityMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CollectivityActivityRepository {

    private final DataSource dataSource;
    private final CollectivityActivityMapper mapper;

    public CollectivityActivityRepository(DataSource dataSource, CollectivityActivityMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    public List<CollectivityActivity> saveAll(String collectivityId, List<CollectivityActivity> activities) {
        String sql = """
                insert into collectivity_activity (id, collectivity_id, label, activity_type,
                    executive_date, recurrence_week_ordinal, recurrence_day_of_week, member_occupations)
                values (?, ?, ?, ?, ?, ?, ?, ?)
                on conflict (id) do update set label = excluded.label,
                                              activity_type = excluded.activity_type
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (CollectivityActivity activity : activities) {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, collectivityId);
                ps.setString(3, activity.getLabel());
                ps.setString(4, activity.getActivityType().name());

                if (activity.getExecutiveDate() != null) {
                    ps.setDate(5, Date.valueOf(activity.getExecutiveDate()));
                    ps.setNull(6, Types.INTEGER);
                    ps.setNull(7, Types.VARCHAR);
                } else if (activity.getRecurrenceRule() != null) {
                    ps.setNull(5, Types.DATE);
                    ps.setInt(6, activity.getRecurrenceRule().getWeekOrdinal());
                    ps.setString(7, activity.getRecurrenceRule().getDayOfWeek());
                } else {
                    ps.setNull(5, Types.DATE);
                    ps.setNull(6, Types.INTEGER);
                    ps.setNull(7, Types.VARCHAR);
                }

                String[] occupations = activity.getMemberOccupationConcerned()
                        .stream()
                        .map(Enum::name)
                        .toArray(String[]::new);
                ps.setArray(8, conn.createArrayOf("text", occupations));
                ps.addBatch();
            }
            ps.executeBatch();
            return findAllByCollectivityId(collectivityId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CollectivityActivity> findAllByCollectivityId(String collectivityId) {
        List<CollectivityActivity> activities = new ArrayList<>();
        String sql = """
                select id, collectivity_id, label, activity_type, executive_date,
                       recurrence_week_ordinal, recurrence_day_of_week, member_occupations
                from collectivity_activity
                where collectivity_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                activities.add(mapper.mapFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return activities;
    }
}