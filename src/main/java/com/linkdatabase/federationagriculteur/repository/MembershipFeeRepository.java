package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.ActivityStatus;
import com.linkdatabase.federationagriculteur.entity.Collectivity;
import com.linkdatabase.federationagriculteur.entity.Frequency;
import com.linkdatabase.federationagriculteur.entity.MembershipFee;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MembershipFeeRepository {
    private final DataSource dataSource;

    public MembershipFeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        List<MembershipFee> fees = new ArrayList<>();
        String sql = """
                SELECT id, eligible_from, frequency, amount, label, status
                FROM membership_fee
                WHERE collectivity_id = ?::uuid
                ORDER BY eligible_from
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, UUID.fromString(collectivityId));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    fees.add(mapRowToMembershipFee(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding membership fees by collectivity", e);
        }
        return fees;
    }

    public MembershipFee insert(MembershipFee fee, String collectivityId) {
        String sql = """
                INSERT INTO membership_fee
                (id, collectivity_id, eligible_from, frequency, amount, label, status)
                VALUES (?::uuid, ?::uuid, ?, ?::fee_frequency, ?, ?, ?::activity_status)
                RETURNING id, eligible_from, frequency, amount, label, status
                """;

        UUID newId = UUID.randomUUID();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, newId);
            stmt.setObject(2, UUID.fromString(collectivityId));
            stmt.setDate(3, Date.valueOf(fee.getEligibleFrom()));
            stmt.setString(4, fee.getFrequency().name());
            stmt.setBigDecimal(5, fee.getAmount());
            stmt.setString(6, fee.getLabel());
            stmt.setString(7, fee.getStatus().name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMembershipFee(rs);
                } else {
                    throw new RuntimeException("Insert failed, no data returned.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting membership fee", e);
        }
    }

    public Optional<Collectivity> findById(String id) {

        String sql = "SELECT id, name FROM collectivity WHERE id = ?::uuid";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, UUID.fromString(id));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Collectivity c = new Collectivity();
                    c.setId(rs.getString("id"));
                    c.setName(rs.getString("name"));
                    return Optional.of(c);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding collectivity", e);
        }

        return Optional.empty();
    }
    public boolean existsByCollectivityIdAndLabel(String collectivityId, String label) {
        String sql = "SELECT 1 FROM membership_fee WHERE collectivity_id = ?::uuid AND label = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, UUID.fromString(collectivityId));
            stmt.setString(2, label);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking membership fee existence", e);
        }
    }

    private MembershipFee mapRowToMembershipFee(ResultSet rs) throws SQLException {
        MembershipFee fee = new MembershipFee();

        fee.setId(rs.getString("id"));
        fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
        fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
        fee.setAmount(rs.getBigDecimal("amount"));
        fee.setLabel(rs.getString("label"));
        fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));

        return fee;
    }

}