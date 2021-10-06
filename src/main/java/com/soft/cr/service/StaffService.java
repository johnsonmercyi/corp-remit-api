package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    
    private final DataAccessModel dao;

    @Autowired
    public StaffService (@Qualifier("postgres_staff") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (Staff user) throws Exception {
        return dao.insert(user);
    }

    public int update (UUID id, Staff user) throws Exception {
        return dao.update(id, user);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }

}
