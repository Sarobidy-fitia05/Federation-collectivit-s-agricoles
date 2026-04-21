package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.Collectivity;
import com.linkdatabase.federationagriculteur.entity.CollectivityStructure;
import com.linkdatabase.federationagriculteur.entity.Member;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityRepository {

    private final DataSource dataSource;
    private final MemberRepository memberRepository;

    public CollectivityRepository(DataSource dataSource, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.memberRepository = memberRepository;
    }

    public Collectivity insertCollectivity(Collectivity collectivity) {
        String sql = """
                INSERT INTO collectivity
                    (location, federation_approval)
                VALUES (?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, collectivity.getLocation());
            statement.setBoolean(2, true); // federationApproval est validé dans le service

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Collectivity insertion failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    collectivity.setId(generatedKeys.getString(1));
                    return collectivity;
                } else {
                    throw new RuntimeException("Collectivity insertion failed, no ID generated.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting collectivity: " + e.getMessage(), e);
        }
    }

    public void insertCollectivityMembers(String collectivityId, List<String> memberIds) {
        String sql = "INSERT INTO collectivity_member (collectivity_id, member_id) VALUES (?::uuid, ?::uuid)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String memberId : memberIds) {
                statement.setString(1, collectivityId);
                statement.setString(2, memberId);
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting collectivity members: " + e.getMessage(), e);
        }
    }

    public void insertCollectivityStructure(String collectivityId, String presidentId,
                                            String vicePresidentId, String treasurerId, String secretaryId) {
        String sql = """
                INSERT INTO collectivity_structure
                    (collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id)
                VALUES (?::uuid, ?::uuid, ?::uuid, ?::uuid, ?::uuid)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, collectivityId);
            statement.setString(2, presidentId);
            statement.setString(3, vicePresidentId);
            statement.setString(4, treasurerId);
            statement.setString(5, secretaryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting collectivity structure: " + e.getMessage(), e);
        }
    }

    public Collectivity findById(String id) {
        String sql = """
                SELECT id, location
                FROM collectivity
                WHERE id = ?::uuid
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Collectivity c = new Collectivity();
                    c.setId(rs.getString("id"));
                    c.setLocation(rs.getString("location"));

                    c.setMembers(findMembersByCollectivityId(id));
                    c.setStructure(findStructureByCollectivityId(id));
                    return c;
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding collectivity by id: " + e.getMessage(), e);
        }
    }

    private List<Member> findMembersByCollectivityId(String collectivityId) {
        String sql = "SELECT member_id FROM collectivity_member WHERE collectivity_id = ?::uuid";
        List<String> memberIds = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, collectivityId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    memberIds.add(rs.getString("member_id"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding collectivity members: " + e.getMessage(), e);
        }

        return memberRepository.findByIds(memberIds);
    }

    private CollectivityStructure findStructureByCollectivityId(String collectivityId) {
        String sql = """
                SELECT president_id, vice_president_id, treasurer_id, secretary_id
                FROM collectivity_structure
                WHERE collectivity_id = ?::uuid
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, collectivityId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    CollectivityStructure structure = new CollectivityStructure();
                    structure.setPresident(memberRepository.findById(rs.getString("president_id")));
                    structure.setVicePresident(memberRepository.findById(rs.getString("vice_president_id")));
                    structure.setTreasurer(memberRepository.findById(rs.getString("treasurer_id")));
                    structure.setSecretary(memberRepository.findById(rs.getString("secretary_id")));
                    return structure;
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding collectivity structure: " + e.getMessage(), e);
        }
    }
}