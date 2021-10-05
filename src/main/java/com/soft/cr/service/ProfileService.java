package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    
    private DataAccessModel dao;

    @Autowired
    public ProfileService (@Qualifier("postgres_profile") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (Profile profile) throws Exception {
        return dao.insert(profile);
    }

    public UUID insertAndReturnId (Profile profile) throws Exception {
        return dao.insertAndReturnId(profile);
    }

    public int update (UUID id, Profile profile) throws Exception {
        return dao.update(id, profile);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }
}
