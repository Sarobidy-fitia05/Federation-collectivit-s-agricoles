package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.FinancialAccount;
import com.linkdatabase.federationagriculteur.entity.FinancialAccountType;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FinancialAccountRepository {

    private final DataSource dataSource;

    public FinancialAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<FinancialAccount> findByCollectivityId(String collectivityId) {

        String sql = """
                SELECT id, amount, collectivity_id, type
                FROM financial_account
                WHERE collectivity_id = ?
                """;

        List<FinancialAccount> accounts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, collectivityId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    FinancialAccount account = new FinancialAccount();

                    account.setId(rs.getString("id"));
                    account.setAmount(rs.getBigDecimal("amount"));
                    account.setCollectivityId(rs.getString("collectivity_id"));

                    String type = rs.getString("type");
                    if (type != null) {
                        account.setType(FinancialAccountType.valueOf(type));
                    }

                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des comptes financiers", e
            );
        }

        return accounts;
    }

    public BigDecimal calculateBalanceAt(String accountId, LocalDate at) {

        String sql = """
                SELECT COALESCE(SUM(amount), 0)
                FROM collectivity_transaction
                WHERE account_credited_id = ?
                AND creation_date <= ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, accountId);
            ps.setDate(2, Date.valueOf(at));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    BigDecimal result = rs.getBigDecimal(1);
                    return result != null ? result : BigDecimal.ZERO;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors du calcul du solde à la date " + at, e
            );
        }

        return BigDecimal.ZERO;
    }


    public FinancialAccount findById(String id) {

        String sql = """
                SELECT id, amount, collectivity_id, type
                FROM financial_account
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    FinancialAccount account = new FinancialAccount();

                    account.setId(rs.getString("id"));
                    account.setAmount(rs.getBigDecimal("amount"));
                    account.setCollectivityId(rs.getString("collectivity_id"));
                    account.setType(FinancialAccountType.valueOf(rs.getString("type")));

                    return account;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération du compte id=" + id, e
            );
        }

        return null;
    }
}