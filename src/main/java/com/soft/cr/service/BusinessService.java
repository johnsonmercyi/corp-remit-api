package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
    
    private DataAccessModel dao;

    @Autowired
    public BusinessService (@Qualifier("postgres_business") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (Business business) throws Exception {
        return dao.insert(business);
    }

    public UUID insertAndReturnId (Business business) throws Exception {
        return dao.insertAndReturnId(business);
    }

    public int update (UUID id, Business business) throws Exception {
        return dao.update(id, business);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }

}
