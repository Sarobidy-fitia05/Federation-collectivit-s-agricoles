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

    private final DataSource dataSourceConfig;

    public StatisticsRepository(DataSource dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    // =========================
    // 1. Montants gagnés par membre sur une période
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
            JOIN collectivity_member cm ON cm.member_id = m.id
            LEFT JOIN member_payment p
                ON p.member_id = m.id
                AND p.creation_date BETWEEN ? AND ?
            WHERE cm.collectivity_id = ?
            GROUP BY m.id, m.first_name, m.last_name
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSourceConfig.getConnection();
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
    // 2. Montants impayés par membre (cotisations actives - paiements)
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
            JOIN collectivity_member cm ON cm.member_id = m.id
            JOIN membership_fee f
                ON f.collectivity_id = cm.collectivity_id
                AND f.status = 'ACTIVE'
                AND f.eligible_from <= ?
            LEFT JOIN member_payment p
                ON p.member_id = m.id
                AND p.membership_fee_id = f.id
            WHERE cm.collectivity_id = ?
            GROUP BY m.id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSourceConfig.getConnection();
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
    // 3. Pourcentage de membres à jour (toutes collectivités)
    // =========================
    public List<Map<String, Object>> getCurrentMembersPercentage() throws SQLException {

        String sql = """
            SELECT
                sub.collectivity_id,
                COUNT(CASE WHEN unpaid = 0 THEN 1 END) * 100.0 / COUNT(*) AS percentage
            FROM (
                SELECT
                    m.id,
                    cm.collectivity_id,
                    COALESCE(SUM(f.amount), 0) - COALESCE(SUM(p.amount), 0) AS unpaid
                FROM member m
                JOIN collectivity_member cm ON cm.member_id = m.id
                JOIN membership_fee f
                    ON f.collectivity_id = cm.collectivity_id
                    AND f.status = 'ACTIVE'
                LEFT JOIN member_payment p
                    ON p.member_id = m.id
                    AND p.membership_fee_id = f.id
                GROUP BY m.id, cm.collectivity_id
            ) sub
            GROUP BY sub.collectivity_id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSourceConfig.getConnection();
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
    // 4. Nouveaux membres sur une période
    // =========================
    public List<Map<String, Object>> getNewMembers(
            LocalDate from,
            LocalDate to
    ) throws SQLException {

        String sql = """
            SELECT
                cm.collectivity_id,
                COUNT(*) AS new_members
            FROM collectivity_member cm
            WHERE cm.joined_at BETWEEN ? AND ?
            GROUP BY cm.collectivity_id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSourceConfig.getConnection();
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

    // =========================
    // 5. Taux d'assiduité par membre pour une collectivité (local)
    // =========================
    public List<Map<String, Object>> getMemberAssiduityByCollectivity(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) throws SQLException {

        String sql = """
            SELECT
                m.id,
                COUNT(CASE WHEN ama.attendance_status = 'ATTENDED' THEN 1 END) * 100.0
                    / NULLIF(
                        COUNT(CASE WHEN ama.attendance_status IN ('ATTENDED', 'MISSING') THEN 1 END),
                        0
                    ) AS assiduity_percentage
            FROM member m
            JOIN collectivity_member cm
                ON cm.member_id = m.id
                AND cm.collectivity_id = ?
            LEFT JOIN collectivity_activity ca
                ON  ca.collectivity_id = cm.collectivity_id
                AND ca.executive_date BETWEEN ? AND ?
            LEFT JOIN activity_member_attendance ama
                ON  ama.activity_id = ca.id
                AND ama.member_id   = m.id
            GROUP BY m.id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSourceConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("assiduity_percentage", rs.getDouble("assiduity_percentage"));
                result.add(row);
            }
        }

        return result;
    }

    // =========================
    // 6. Taux d'assiduité global par collectivité (overall)
    // =========================
    public List<Map<String, Object>> getOverallAssiduityByCollectivity(
            LocalDate from,
            LocalDate to
    ) throws SQLException {

        String sql = """
            SELECT
                ca.collectivity_id,
                COUNT(CASE WHEN ama.attendance_status = 'ATTENDED' THEN 1 END) * 100.0
                    / NULLIF(
                        COUNT(CASE WHEN ama.attendance_status IN ('ATTENDED', 'MISSING') THEN 1 END),
                        0
                    ) AS assiduity_percentage
            FROM collectivity_activity ca
            LEFT JOIN activity_member_attendance ama
                ON ama.activity_id = ca.id
            WHERE ca.executive_date BETWEEN ? AND ?
            GROUP BY ca.collectivity_id
        """;

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection conn = dataSourceConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("collectivity_id", rs.getString("collectivity_id"));
                row.put("assiduity_percentage", rs.getDouble("assiduity_percentage"));
                result.add(row);
            }
        }

        return result;
    }
}