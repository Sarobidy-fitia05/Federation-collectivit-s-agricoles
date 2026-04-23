package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.CollectivityTransaction;
import com.linkdatabase.federationagriculteur.entity.FinancialAccount;
import com.linkdatabase.federationagriculteur.entity.Member;
import com.linkdatabase.federationagriculteur.entity.PaymentMode;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityTransactionRepository {

    private final DataSource dataSource;

    public CollectivityTransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<CollectivityTransaction> findByCollectivityIdAndDateBetween(
            String collectivityId,
            LocalDate from,
            LocalDate to
    ) {

        String sql = """
                SELECT collectivity_transaction.id,
                       collectivity_transaction.creation_date,
                       collectivity_transaction.amount,
                       collectivity_transaction.payment_mode,
                       collectivity_transaction.account_credited_id,
                       financial_account.id AS financial_account_id,
                       collectivity_transaction.member_debited_id,
                       member.id AS member_id
                FROM collectivity_transaction
                JOIN financial_account ON collectivity_transaction.account_credited_id = financial_account.id
                JOIN member ON collectivity_transaction.member_debited_id = member.id
                WHERE collectivity_transaction.collectivity_id = ?
                  AND collectivity_transaction.creation_date BETWEEN ? AND ?
                """;

        List<CollectivityTransaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, collectivityId);
            statement.setDate(2, Date.valueOf(from));
            statement.setDate(3, Date.valueOf(to));

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    CollectivityTransaction transaction = new CollectivityTransaction();

                    transaction.setId(resultSet.getString("id"));
                    transaction.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                    transaction.setAmount(resultSet.getBigDecimal("amount"));

                    transaction.setPaymentMode(
                            PaymentMode.valueOf(resultSet.getString("payment_mode"))
                    );

                    FinancialAccount account = new FinancialAccount();
                    account.setId(resultSet.getString("financial_account_id"));
                    transaction.setAccountCredited(account);

                    Member member = new Member();
                    member.setId(resultSet.getString("member_id"));
                    transaction.setMemberDebited(member);

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error retrieving collectivity transactions: " + e.getMessage(), e
            );
        }

        return transactions;
    }
}