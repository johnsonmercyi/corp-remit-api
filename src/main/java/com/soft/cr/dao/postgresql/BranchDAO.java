package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_branch")
public class BranchDAO implements DataAccessModel {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BranchDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int delete(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        Branch branch = (Branch) object;
        final String sql = "INSERT INTO branches VALUES (?,?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql, new Object[]{
            id,
            branch.getName(),
            branch.getAddress(),
            branch.getBusinessId(),
            LocalDateTime.now(),
            LocalDateTime.now()
        });
        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT * FROM branches;";
        return jdbcTemplate.query(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Branch(
                UUID.fromString(rs.getString("branch_id")),
                rs.getString("name"),
                rs.getString("address"),
                UUID.fromString(rs.getString("business_id")),
                createdAt,
                updatedAt
            );
        });
    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {
        final String sql = "SELECT * FROM branches WHERE branch_id=?;";
        Branch branch = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Branch(
                UUID.fromString(rs.getString("branch_id")),
                rs.getString("name"),
                rs.getString("address"),
                UUID.fromString(rs.getString("business_id")),
                createdAt,
                updatedAt
            );
        }, new Object[]{id});

        return Optional.ofNullable(branch);
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        Branch oldBranch = (Branch) this.read(id).orElse(null);
        Branch newBranch = (Branch) object;
        Branch mainBranch = null;
        int rowsAffected = 0;

        if (oldBranch == null) {
            rowsAffected = this.insert(id, object);
        } else {
            mainBranch = new Branch(
                id,
                newBranch.getName(), 
                newBranch.getAddress(), 
                newBranch.getBusinessId(), 
                null,//we're not updating this column.
                LocalDateTime.now()
            );

            final String sql = "UPDATE branches SET "
                                +"name=?, "
                                +"address=?, "
                                +"business_id=?, "
                                +"updated_at=? "
                                +"WHERE branch_id=?";

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainBranch.getName(),
                mainBranch.getAddress(),
                mainBranch.getBusinessId(),
                mainBranch.getUpdatedAt(),
                mainBranch.getId()
            });
        }

        return rowsAffected;
    }
    
}
