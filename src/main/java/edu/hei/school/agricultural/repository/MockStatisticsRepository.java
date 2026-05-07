package edu.hei.school.agricultural.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@Primary
public class MockStatisticsRepository extends StatisticsRepository {

    public MockStatisticsRepository() {
        super(null); // pas besoin de DataSource pour mock
    }

    @Override
    public List<Map<String, Object>> getEarnedAmountByMember(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) {

        return List.of(
                Map.of(
                        "id", "M1",
                        "first_name", "John",
                        "last_name", "Doe",
                        "earned_amount", 120.0
                )
        );
    }

    @Override
    public List<Map<String, Object>> getUnpaidAmountByMember(
            String collectivityId,
            LocalDate to
    ) {

        return List.of(
                Map.of(
                        "id", "M1",
                        "unpaid_amount", 30.0
                )
        );
    }

    @Override
    public List<Map<String, Object>> getCurrentMembersPercentage() {

        return List.of(
                Map.of(
                        "collectivity_id", "C1",
                        "percentage", 75.0
                )
        );
    }

    @Override
    public List<Map<String, Object>> getNewMembers(
            LocalDate from,
            LocalDate to
    ) {

        return List.of(
                Map.of(
                        "collectivity_id", "C1",
                        "new_members", 5
                )
        );
    }
}