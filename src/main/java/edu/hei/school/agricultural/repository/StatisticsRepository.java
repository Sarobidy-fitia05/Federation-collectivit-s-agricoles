package edu.hei.school.agricultural.repository;

import edu.hei.school.agricultural.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatisticsRepository {

    private final DataSource dataSource;

    public StatisticsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // =========================
    // 1. Earned Amount
    // =========================
    public List<Map<String, Object>> getEarnedAmountByMember(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws SQLException {

        String sql = """
            SELECT 
                m.id,
                m.first_name,
                m.last_name,
                COALESCE(SUM(p.amount), 0) AS earned_amount
            FROM member m
            LEFT JOIN member_payment p 
                ON p.member_id = m.id
                AND p.creation_date BETWEEN ? AND ?
            WHERE m.collectivity_id = ?
            GROUP BY m.id, m.first_name, m.last_name
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            ps.setString(3, collectivityId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("first_name", rs.getString("first_name"));
                row.put("last_name", rs.getString("last_name"));
                row.put("earned_amount", rs.getDouble("earned_amount"));
                result.add(row);
            }
        }

        return result;
    }

    // =========================
    // 2. Unpaid Amount
    // =========================
    public List<Map<String, Object>> getUnpaidAmountByMember(
            String collectivityId,
            LocalDate to
    ) throws SQLException {

        String sql = """
            SELECT 
                m.id,
                COALESCE(SUM(f.amount), 0) - COALESCE(SUM(p.amount), 0) AS unpaid_amount
            FROM member m
            JOIN membership_fee f 
                ON f.collectivity_id = m.collectivity_id
                AND f.status = 'ACTIVE'
                AND f.eligible_from <= ?
            LEFT JOIN member_payment p 
                ON p.member_id = m.id
                AND p.membership_fee_id = f.id
            WHERE m.collectivity_id = ?
            GROUP BY m.id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(to));
            ps.setString(2, collectivityId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("unpaid_amount", rs.getDouble("unpaid_amount"));
                result.add(row);
            }
        }

        return result;
    }

    // =========================
    // 3. % membres à jour
    // =========================
    public List<Map<String, Object>> getCurrentMembersPercentage() throws SQLException {

        String sql = """
            SELECT 
                sub.collectivity_id,
                COUNT(CASE WHEN unpaid = 0 THEN 1 END) * 100.0 / COUNT(*) AS percentage
            FROM (
                SELECT 
                    m.id,
                    m.collectivity_id,
                    COALESCE(SUM(f.amount), 0) - COALESCE(SUM(p.amount), 0) AS unpaid
                FROM member m
                JOIN membership_fee f 
                    ON f.collectivity_id = m.collectivity_id
                    AND f.status = 'ACTIVE'
                LEFT JOIN member_payment p 
                    ON p.member_id = m.id
                    AND p.membership_fee_id = f.id
                GROUP BY m.id, m.collectivity_id
            ) sub
            GROUP BY sub.collectivity_id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("collectivity_id", rs.getString("collectivity_id"));
                row.put("percentage", rs.getDouble("percentage"));
                result.add(row);
            }
        }

        return result;
    }

    // =========================
    // 4. Nouveaux membres
    // =========================
    public List<Map<String, Object>> getNewMembers(
            LocalDate from,
            LocalDate to
    ) throws SQLException {

        String sql = """
            SELECT 
                collectivity_id,
                COUNT(*) AS new_members
            FROM member
            WHERE created_at BETWEEN ? AND ?
            GROUP BY collectivity_id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("collectivity_id", rs.getString("collectivity_id"));
                row.put("new_members", rs.getInt("new_members"));
                result.add(row);
            }
        }

        return result;
    }
}
