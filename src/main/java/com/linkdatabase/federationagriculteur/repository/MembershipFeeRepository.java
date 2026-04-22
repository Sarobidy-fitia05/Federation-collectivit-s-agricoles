package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.MembershipFee;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembershipFeeRepository {
    private DataSource dataSource;

    public MembershipFeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        List <MembershipFee> fees = new ArrayList<>();

        String sql =
                """
                      SELECT id, eligible_from, frequency, amount, label, status FROM membership_fee 
                      WHERE collectivity_id = ?::uuid ORDER BY eligible_from
                """;

        try   (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                )
        {
            stmt.setString(1,collectivityId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();

                fee.setId(rs.getString("id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(rs.getString("frequency"));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(rs.getString("status"));

                fees.add(fee);
            }
        }
        catch (SQLException e)  {
            throw new RuntimeException("Error finding membership fees: " + e.getMessage(), e);
        }
        return fees;

    }

 import java.util.UUID;
import java.sql.Date;

    public MembershipFee insert(MembershipFee fee, String collectivityId) {

        String sql = """
        INSERT INTO membership_fee
        (id, collectivity_id, eligible_from, frequency, amount, label, status)
        VALUES (?::uuid, ?::uuid, ?, ?, ?, ?, ?)
        RETURNING id, collectivity_id, eligible_from, frequency, amount, label, status
    """;

        String generatedId = UUID.randomUUID().toString();

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setString(1, generatedId);
            stmt.setString(2, collectivityId);
            stmt.setDate(3, Date.valueOf(fee.getEligibleFrom()));
            stmt.setString(4, fee.getFrequency());
            stmt.setBigDecimal(5, fee.getAmount());
            stmt.setString(6, fee.getLabel());
            stmt.setString(7, fee.getStatus());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    MembershipFee inserted = new MembershipFee();

                    inserted.setId(rs.getString("id"));
                    inserted.setCollectivityId(rs.getString("collectivity_id"));
                    inserted.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                    inserted.setFrequency(rs.getString("frequency"));
                    inserted.setAmount(rs.getBigDecimal("amount"));
                    inserted.setLabel(rs.getString("label"));
                    inserted.setStatus(rs.getString("status"));

                    return inserted;
                } else {
                    throw new RuntimeException("Insert failed, no data returned.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting membership fee", e);
        }
    }
}
