package com.soft.cr.dao.postgresql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
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
        final Role role = (Role) object;
        final String sql = "INSERT INTO roles VALUES (?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, new Object[]{
            id,
            role.getDesc(),
            role.getType(),
            role.getCreatedAt(),
            role.getUpdatedAt()
        });

        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
