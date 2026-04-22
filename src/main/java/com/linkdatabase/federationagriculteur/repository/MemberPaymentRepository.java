package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.MemberPayment;
import com.linkdatabase.federationagriculteur.exception.ConflictException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class MemberPaymentRepository {

    private final DataSource dataSource;

    public MemberPaymentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MemberPayment insert(MemberPayment payment) {

        LocalDateTime now = LocalDateTime.now();
        payment.setCreationDate(now);

        String sql = """
            INSERT INTO member_payment
            (id, amount, membership_fee_id, financial_account_id, payment_mode, creation_date, member_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, payment.getId());
            stmt.setBigDecimal(2, payment.getAmount());
            stmt.setString(3, payment.getMembershipFeeId());
            stmt.setString(4, payment.getFinancialAccountId());
            stmt.setString(5, payment.getPaymentMode().name());
            stmt.setTimestamp(6, Timestamp.valueOf(now));
            stmt.setString(7, payment.getMemberId());

            stmt.executeUpdate();
            return payment;

        } catch (SQLException e) {

            if (e.getSQLState().startsWith("23")
                || e.getMessage().toLowerCase().contains("duplicate")) {

                throw new ConflictException(
                        "Payment with id '" + payment.getId() + "' already exists"
                );
            }

            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public boolean existsById(String id) {

        String sql = "SELECT COUNT(id) FROM member_payment WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }

        return false;
    }
}