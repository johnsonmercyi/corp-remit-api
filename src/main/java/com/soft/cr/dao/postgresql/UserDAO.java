package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.User;

// import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;

@Repository("postgres")
public class UserDAO implements DataAccessModel {

    private final JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate
     */
    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT * FROM users;";

        return jdbcTemplate.query(sql, (rs, i) -> {

            LocalDateTime lastLogin = rs.getString("last_login") != null ? LocalDateTime.parse(
                    rs.getString("last_login").substring(0, rs.getString("last_login").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new User(UUID.fromString(rs.getString("user_id")), UUID.fromString(rs.getString("profile_id")),
                    rs.getString("username"), rs.getString("password"), rs.getString("email"),
                    rs.getString("remember_me_token"), UUID.fromString(rs.getString("role_id")), lastLogin, createdAt,
                    updatedAt);
        });

    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {

        final String sql = "SELECT * FROM users WHERE user_id = ?";

        User user = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime lastLogin = rs.getString("last_login") != null ? LocalDateTime.parse(
                    rs.getString("last_login").substring(0, rs.getString("last_login").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new User(UUID.fromString(rs.getString("user_id")), UUID.fromString(rs.getString("profile_id")),
                    rs.getString("username"), rs.getString("password"), rs.getString("email"),
                    rs.getString("remember_me_token"), UUID.fromString(rs.getString("role_id")), lastLogin, createdAt,
                    updatedAt);
        }, new Object[] { id });

        return Optional.ofNullable(user);
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

}
