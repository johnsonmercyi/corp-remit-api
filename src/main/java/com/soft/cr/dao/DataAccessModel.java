package com.soft.cr.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataAccessModel {
    
    List<Object> read() throws Exception;

    Optional<Object> read(UUID id) throws Exception;

    int insert (UUID id, Object object) throws Exception;

    default int insert (Object object) throws Exception {
        UUID id = UUID.randomUUID();
        return insert(id, object);
    }

    default UUID insertAndReturnId (Object object) throws Exception {
        UUID id = UUID.randomUUID();
        int insertedRows = insert(id, object);
        if (insertedRows > 0) {
            return id;
        }
        return null;
    }

    int update(UUID id, Object object) throws Exception;

    int delete(UUID id) throws Exception;

}
