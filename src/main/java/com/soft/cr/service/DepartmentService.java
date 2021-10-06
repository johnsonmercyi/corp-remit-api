package com.soft.cr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private DataAccessModel dao;

    @Autowired
    public DepartmentService (@Qualifier("postgres_department") DataAccessModel dao) {
        this.dao = dao;
    }

    public List<Object> read () throws Exception {
        return dao.read();
    }

    public Optional<Object> read (UUID id) throws Exception {
        return dao.read(id);
    }

    public int insert (Department department) throws Exception {
        return dao.insert(department);
    }

    public UUID insertAndReturnId (Department department) throws Exception {
        return dao.insertAndReturnId(department);
    }

    public int update (UUID id, Department department) throws Exception {
        return dao.update(id, department);
    }

    public int delete (UUID id) throws Exception {
        return dao.delete(id);
    }
}
