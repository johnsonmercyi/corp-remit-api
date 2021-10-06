package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_department")
public class DepartmentDAO implements DataAccessModel{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int delete(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        final Department department = (Department) object;
        final String sql = "INSERT INTO departments VALUES (?,?,?,?,?)";

        int rowsAffected = jdbcTemplate.update(sql, new Object[]{
            id,
            department.getName(),
            department.getBranchId(),
            LocalDateTime.now(),
            LocalDateTime.now()
        });

        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT * FROM departments;";
        return jdbcTemplate.query(sql, (rs, i) -> {
            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Department(
                UUID.fromString(rs.getString("department_id")),
                rs.getString("name"),
                UUID.fromString(rs.getString("branch_id")),
                createdAt,
                updatedAt
            );
        });
    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {
        final String sql = "SELECT * FROM departments WHERE department_id=?;";
        Department department = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Department(
                UUID.fromString(rs.getString("department_id")),
                rs.getString("name"),
                UUID.fromString(rs.getString("branch_id")),
                createdAt,
                updatedAt
            );
        }, new Object[]{id});

        return Optional.ofNullable(department);
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        Department oldDepartment = (Department) this.read(id).orElse(null);
        Department newDepartment = (Department) object;
        Department mainDepartment = null;
        int rowsAffected = 0;

        if (oldDepartment == null) {
            rowsAffected = this.insert(id, object);
        } else {
            mainDepartment = new Department(
                id,
                newDepartment.getName(), 
                newDepartment.getBranchId(),
                null,//we're not updating this column.
                LocalDateTime.now()
            );

            final String sql = "UPDATE departments SET "
                                +"name=?, "
                                +"branch_id=?, "
                                +"updated_at=? "
                                +"WHERE department_id=?";

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainDepartment.getName(),
                mainDepartment.getBranchId(),
                mainDepartment.getUpdatedAt(),
                mainDepartment.getId()
            });
        }

        return rowsAffected;
    }
    
}
