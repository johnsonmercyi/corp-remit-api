package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final DataAccessModel dao;

    @Autowired
    public UserService (@Qualifier("postgres") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (User user) throws Exception {
        return dao.insert(user);
    }

    public int update (UUID id, User user) throws Exception {
        return dao.update(id, user);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }
}
