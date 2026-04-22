package com.linkdatabase.federationagriculteur.repository;

import com.linkdatabase.federationagriculteur.entity.Gender;
import com.linkdatabase.federationagriculteur.entity.Member;
import com.linkdatabase.federationagriculteur.entity.MemberOccupation;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class MemberRepository {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member insertMember(Member member) {
        String sql = """
                INSERT INTO member
                    (id, first_name, last_name, birth_date, gender, address,
                     profession, phone_number, email, occupation, membership_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, member.getId());
            statement.setString(2, member.getFirstName());
            statement.setString(3, member.getLastName());
            statement.setDate(4, member.getBirthDate() != null ? Date.valueOf(member.getBirthDate()) : null);
            statement.setString(5, member.getGender() != null ? member.getGender().name() : null);
            statement.setString(6, member.getAddress());
            statement.setString(7, member.getProfession());
            statement.setObject(8, member.getPhoneNumber());
            statement.setString(9, member.getEmail());
            statement.setString(10, member.getOccupation() != null ? member.getOccupation().name() : null);
            statement.setDate(11, Date.valueOf(LocalDate.now()));

            statement.executeUpdate();
            return member;

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting member: " + e.getMessage(), e);
        }
    }

    public void insertRefereeLinks(String memberId, List<String> refereeIds) {
        String sql = "INSERT INTO member_referee (member_id, referee_id) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (String refereeId : refereeIds) {
                statement.setString(1, memberId);
                statement.setString(2, refereeId);
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting referee links: " + e.getMessage(), e);
        }
    }

    public Member findById(String id) {
        String sql = """
                SELECT id, first_name, last_name, birth_date, gender, address,
                       profession, phone_number, email, occupation
                FROM member
                WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding member with id=" + id + ": " + e.getMessage(), e);
        }
    }

    public List<Member> findByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        String placeholders = String.join(", ", Collections.nCopies(ids.size(), "?"));
        String sql = """
                SELECT id, first_name, last_name, birth_date, gender, address,
                       profession, phone_number, email, occupation
                FROM member
                WHERE id IN (""" + placeholders + ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < ids.size(); i++) {
                statement.setString(i + 1, ids.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Member> members = new ArrayList<>();
                while (resultSet.next()) {
                    members.add(mapRow(resultSet));
                }
                return members;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding members by IDs: " + e.getMessage(), e);
        }
    }

    private Member mapRow(ResultSet resultSet) throws SQLException {
        Member member = new Member();

        member.setId(resultSet.getString("id"));
        member.setFirstName(resultSet.getString("first_name"));
        member.setLastName(resultSet.getString("last_name"));

        Date birthDate = resultSet.getDate("birth_date");
        if (birthDate != null) {
            member.setBirthDate(birthDate.toLocalDate());
        }

        String genderStr = resultSet.getString("gender");
        if (genderStr != null) {
            member.setGender(Gender.valueOf(genderStr));
        }

        member.setAddress(resultSet.getString("address"));
        member.setProfession(resultSet.getString("profession"));
        member.setPhoneNumber(resultSet.getObject("phone_number", Integer.class));
        member.setEmail(resultSet.getString("email"));

        String occupationStr = resultSet.getString("occupation");
        if (occupationStr != null) {
            member.setOccupation(MemberOccupation.valueOf(occupationStr));
        }

        member.setReferees(Collections.emptyList());

        return member;
    }
}