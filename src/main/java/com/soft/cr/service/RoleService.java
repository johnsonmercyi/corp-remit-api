package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RoleService {
    private DataAccessModel dao;

    @Autowired
    public RoleService (@Qualifier("postgres_role") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (Role role) throws Exception {
        return dao.insert(role);
    }

    public UUID insertAndReturnId (Role role) throws Exception {
        return dao.insertAndReturnId(role);
    }

    public int update (UUID id, Role role) throws Exception {
        return dao.update(id, role);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }
}
