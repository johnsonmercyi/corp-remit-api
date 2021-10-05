package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_business")
public class BusinessDAO implements DataAccessModel {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BusinessDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int delete(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        Business business = (Business) object;
        final String sql = "INSERT INTO businesses VALUES (?,?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql, new Object[]{
            id,
            business.getUserId(),
            business.getName(),
            business.getRcNo(),
            LocalDateTime.now(),
            LocalDateTime.now()
        });
        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT * FROM businesses;";
        return jdbcTemplate.query(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Business(
                UUID.fromString(rs.getString("business_id")),
                UUID.fromString(rs.getString("user_id")),
                rs.getString("name"),
                rs.getString("rc_no"),
                createdAt,
                updatedAt
            );
        });
    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {
        final String sql = "SELECT * FROM businesses WHERE business_id=?;";
        Business business = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Business(
                UUID.fromString(rs.getString("business_id")),
                UUID.fromString(rs.getString("user_id")),
                rs.getString("name"),
                rs.getString("rc_no"),
                createdAt,
                updatedAt
            );
        }, new Object[]{id});

        return Optional.ofNullable(business);
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        Business oldBusiness = (Business) this.read(id).orElse(null);
        Business newBusiness = (Business) object;
        Business mainBusiness = null;
        int rowsAffected = 0;

        if (oldBusiness == null) {
            rowsAffected = this.insert(id, object);
        } else {
            mainBusiness = new Business(
                id,
                null,//we're not updating this column. 
                newBusiness.getName(), 
                newBusiness.getRcNo(), 
                null,//we're not updating this column.
                LocalDateTime.now()
            );

            final String sql = "UPDATE businesses SET "
                                +"name=?, "
                                +"rc_no=?, "
                                +"updated_at=? "
                                +"WHERE business_id=?";

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainBusiness.getName(),
                mainBusiness.getRcNo(),
                mainBusiness.getUpdatedAt(),
                mainBusiness.getId()
            });
        }

        return rowsAffected;
    }

}
