package edu.hei.school.agricultural.repository;

import edu.hei.school.agricultural.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class AttendanceRepository {

    private final DataSource dataSourceConfig;

    public AttendanceRepository(DataSource dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    // Vérifie que l'activité appartient bien à la collectivité
    public boolean activityBelongsToCollectivity(
            String collectivityId, String activityId
    ) throws SQLException {
        String sql = """
            SELECT 1 FROM collectivity_activity
            WHERE id = ? AND collectivity_id = ?
        """;
        try (Connection conn = dataSourceConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ps.setString(2, collectivityId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    // Récupère le statut actuel d'un membre pour une activité
    public String getCurrentStatus(
            String activityId, String memberId
    ) throws SQLException {
        String sql = """
            SELECT attendance_status
            FROM activity_member_attendance
            WHERE activity_id = ? AND member_id = ?
        """;
        try (Connection conn = dataSourceConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("attendance_status");
            return null; // pas encore de ligne
        }
    }

    // INSERT ou UPDATE selon existence
    public Map<String, Object> upsertAttendance(
            String activityId, String memberId, String status
    ) throws SQLException {
        String existing = getCurrentStatus(activityId, memberId);

        if (existing == null) {
            // INSERT
            String sql = """
                INSERT INTO activity_member_attendance
                    (id, activity_id, member_id, attendance_status)
                VALUES (gen_random_uuid(), ?, ?, ?)
                RETURNING id, member_id, attendance_status
            """;
            try (Connection conn = dataSourceConfig.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, activityId);
                ps.setString(2, memberId);
                ps.setString(3, status);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return toRow(rs);
            }
        } else if ("UNDEFINED".equals(existing)) {
            // UPDATE seulement si UNDEFINED
            String sql = """
                UPDATE activity_member_attendance
                SET attendance_status = ?
                WHERE activity_id = ? AND member_id = ?
                RETURNING id, member_id, attendance_status
            """;
            try (Connection conn = dataSourceConfig.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, status);
                ps.setString(2, activityId);
                ps.setString(3, memberId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return toRow(rs);
            }
        } else {
            // ATTENDED ou MISSING : immuable
            return null;
        }
        return null;
    }

    // Récupère toutes les présences d'une activité avec info membre
    public List<Map<String, Object>> getAttendanceByActivity(
            String activityId
    ) throws SQLException {
        String sql = """
            SELECT
                ama.id,
                ama.attendance_status,
                m.id        AS member_id,
                m.first_name,
                m.last_name,
                m.email,
                m.occupation
            FROM activity_member_attendance ama
            JOIN member m ON m.id = ama.member_id
            WHERE ama.activity_id = ?
        """;
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = dataSourceConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id",                rs.getString("id"));
                row.put("attendance_status", rs.getString("attendance_status"));
                row.put("member_id",         rs.getString("member_id"));
                row.put("first_name",        rs.getString("first_name"));
                row.put("last_name",         rs.getString("last_name"));
                row.put("email",             rs.getString("email"));
                row.put("occupation",        rs.getString("occupation"));
                result.add(row);
            }
        }
        return result;
    }

    private Map<String, Object> toRow(ResultSet rs) throws SQLException {
        Map<String, Object> row = new HashMap<>();
        row.put("id",                rs.getString("id"));
        row.put("member_id",         rs.getString("member_id"));
        row.put("attendance_status", rs.getString("attendance_status"));
        return row;
    }
}