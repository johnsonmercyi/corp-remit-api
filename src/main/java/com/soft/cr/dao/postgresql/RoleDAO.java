package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_role")
public class RoleDAO implements DataAccessModel {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int delete(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        //Firstly we check that we're not attempting a data duplicate infringement :)
        boolean isRoleExist = this.isRoleExist((Role)object);
        if (isRoleExist) throw new DuplicateKeyException("Record already exists.");

        final Role role = (Role) object;
        final String sql = "INSERT INTO roles VALUES (?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, new Object[]{
            id,
            role.getName(),
            role.getType(),
            LocalDateTime.now(),
            LocalDateTime.now()
        });

        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT * FROM roles;";
        return jdbcTemplate.query(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Role(
                UUID.fromString(rs.getString("role_id")),
                rs.getString("name"),
                rs.getString("type"),
                createdAt,
                updatedAt
            );
        });
    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {
        final String sql = "SELECT * FROM roles WHERE role_id=?;";
        Role role = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Role(
                UUID.fromString(rs.getString("role_id")),
                rs.getString("name"),
                rs.getString("type"),
                createdAt,
                updatedAt
            );
        }, new Object[]{id});

        return Optional.ofNullable(role);
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        Role oldRole = (Role) this.read(id).orElse(null);
        Role newRole = (Role) object;
        Role mainRole = null;
        int rowsAffected = 0;

        //Meanwhile check that we're not attempting a data duplicate infringement :)
        boolean isRoleExist = this.isRoleExist(newRole);
        if (isRoleExist) throw new DuplicateKeyException("Record already exists.");

        if (oldRole == null) {
            rowsAffected = this.insert(id, object);
        } else {
            mainRole = new Role(
                id,
                newRole.getName(), 
                newRole.getType(),
                null,//we're not updating this column.
                LocalDateTime.now()
            );

            final String sql = "UPDATE roles SET "
                                +"name=?, "
                                +"type=?, "
                                +"updated_at=? "
                                +"WHERE role_id=?";

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainRole.getName(),
                mainRole.getType(),
                mainRole.getUpdatedAt(),
                mainRole.getId()
            });
        }

        return rowsAffected;
    }

    private boolean isRoleExist (Role testRole) {
        final String sql = "SELECT * FROM roles WHERE name=? AND type=?";
        Role role = null;
        try {
            role = jdbcTemplate.queryForObject(sql, (rs, i) -> {
                return new Role(
                    null,
                    rs.getString("name"),
                    rs.getString("type"),
                    null,
                    null
                );
            }, new Object[]{testRole.getName(), testRole.getType()});
        }catch (EmptyResultDataAccessException ex) {
            
        }

        return role != null;
    }
    
}
