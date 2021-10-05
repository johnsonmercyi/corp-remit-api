package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BranchService {
    private DataAccessModel dao;

    @Autowired
    public BranchService (@Qualifier("postgres_branch") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (Branch branch) throws Exception {
        return dao.insert(branch);
    }

    public UUID insertAndReturnId (Branch branch) throws Exception {
        return dao.insertAndReturnId(branch);
    }

    public int update (UUID id, Branch branch) throws Exception {
        return dao.update(id, branch);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }
}
