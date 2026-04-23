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
        String sql = "INSERT INTO collectivite (ville, autorisation_ouverture) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, collectivity.getLocation());
            statement.setBoolean(2, true);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Collectivity insertion failed.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    collectivity.setId(generatedKeys.getString(1));
                    return collectivity;
                } else {
                    throw new RuntimeException("No ID generated.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting collectivity: " + e.getMessage(), e);
        }
    }

    // --- MÉTHODES RÉINTÉGRÉES POUR LE SERVICE ---

    public void insertCollectivityMembers(String collectivityId, List<String> memberIds) {
        // Note: Assure-toi que la table 'collectivity_member' existe en SQL
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
            throw new RuntimeException("Error inserting members: " + e.getMessage(), e);
        }
    }

    public void insertCollectivityStructure(String collectivityId, String presidentId,
                                            String vicePresidentId, String treasurerId, String secretaryId) {
        // Note: Assure-toi que la table 'collectivity_structure' existe en SQL
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
            throw new RuntimeException("Error inserting structure: " + e.getMessage(), e);
        }
    }

    // --- RECHERCHE ET VALIDATION ---

    public Collectivity findById(String id) {
        String sql = """
                SELECT id_collectivite as id, ville as location, numero_unique as number, nom as name
                FROM collectivite
                WHERE id_collectivite = ?::uuid
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding collectivity: " + e.getMessage(), e);
        }
    }

    public boolean existsByNumber(String number) {
        String sql = "SELECT 1 FROM collectivite WHERE numero_unique = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(number));
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { return false; }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM collectivite WHERE nom = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { return false; }
    }

    public Collectivity updateNumberAndName(String collectivityId, String number, String name) {
        String sql = "UPDATE collectivite SET numero_unique = ?, nom = ? WHERE id_collectivite = ?::uuid";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(number));
            statement.setString(2, name);
            statement.setString(3, collectivityId);
            statement.executeUpdate();
            return findById(collectivityId);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating: " + e.getMessage(), e);
        }
    }

    // --- MAPPING ---

    private Collectivity mapRow(ResultSet rs) throws SQLException {
        Collectivity c = new Collectivity();
        c.setId(rs.getString("id"));
        c.setLocation(rs.getString("location"));
        c.setNumber(rs.getString("number"));
        c.setName(rs.getString("name"));

        // On tente de charger les membres et la structure uniquement si les tables existent
        try {
            c.setMembers(findMembersByCollectivityId(c.getId()));
            c.setStructure(findStructureByCollectivityId(c.getId()));
        } catch (Exception e) {
            // Si les tables de jointures n'existent pas encore, on laisse les listes vides
        }
        return c;
    }

    private List<Member> findMembersByCollectivityId(String collectivityId) {
        String sql = "SELECT member_id FROM collectivity_member WHERE collectivity_id = ?::uuid";
        List<String> memberIds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, collectivityId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) { memberIds.add(rs.getString("member_id")); }
            }
        } catch (SQLException e) { return new ArrayList<>(); }
        return memberRepository.findByIds(memberIds);
    }

    private CollectivityStructure findStructureByCollectivityId(String collectivityId) {
        String sql = "SELECT president_id, vice_president_id, treasurer_id, secretary_id FROM collectivity_structure WHERE collectivity_id = ?::uuid";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, collectivityId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    CollectivityStructure s = new CollectivityStructure();
                    s.setPresident(memberRepository.findById(rs.getString("president_id")));
                    s.setVicePresident(memberRepository.findById(rs.getString("vice_president_id")));
                    s.setTreasurer(memberRepository.findById(rs.getString("treasurer_id")));
                    s.setSecretary(memberRepository.findById(rs.getString("secretary_id")));
                    return s;
                }
            }
        } catch (SQLException e) { return null; }
        return null;
    }
}