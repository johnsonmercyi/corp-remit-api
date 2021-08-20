package com.soft.cr.dao.postgresql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;

import org.springframework.stereotype.Repository;

@Repository("postgres")
public class UserDAO implements DataAccessModel {

    // private final JdbcTemplate jdbcTemplate;

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
