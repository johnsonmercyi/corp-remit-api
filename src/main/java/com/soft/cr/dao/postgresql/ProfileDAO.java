package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_profile")
public class ProfileDAO implements DataAccessModel {

    private final JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate
     */
    @Autowired
    public ProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int delete(UUID id) throws Exception {
        Profile profile = (Profile) this.read(id).orElse(null);
        if (profile == null) return 0;
        
        return jdbcTemplate.update("DELETE FROM profiles WHERE profile_id=?", new Object[]{id});
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        Profile profile = (Profile) object;

        String sql = "INSERT INTO profiles VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql,
                new Object[] { id, profile.getFirstName(), profile.getLastName(), profile.getGender(), profile.getDob(),
                        profile.getNationality(), profile.getCountryOfResidence(), profile.getCity(),
                        profile.getAddress1(), profile.getAddress2(), profile.getPostalCode(), profile.getMobile(),
                        profile.getAvarta(), profile.getCreatedAt(), profile.getUpdatedAt() });
        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT * FROM profiles;";
        return jdbcTemplate.query(sql, (rs, i) -> {

            // LocalDate dob = rs.getString("dob") != null
            //         ? LocalDate.parse(rs.getString("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            //         : null;

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Profile(UUID.fromString(rs.getString("profile_id")), 
                rs.getString("first_name"),
                rs.getString("last_name"), 
                rs.getString("gender"), 
                rs.getString("dob"), 
                rs.getString("nationality"),
                rs.getString("country_of_residence"), 
                rs.getString("city"), 
                rs.getString("address_1"),
                rs.getString("address_2"), 
                rs.getString("postal_code"), 
                rs.getString("mobile"),
                rs.getString("avarta"), 
                createdAt, 
                updatedAt
            );
        });
    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {
        final String sql = "SELECT * FROM profiles WHERE profile_id=?";
        Profile profile = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            // LocalDate dob = rs.getString("dob") != null
            //         ? LocalDate.parse(rs.getString("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            //         : null;

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Profile(UUID.fromString(rs.getString("profile_id")), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getString("gender"), 
                    rs.getString("dob"), 
                    rs.getString("nationality"),
                    rs.getString("country_of_residence"), rs.getString("city"), rs.getString("address_1"),
                    rs.getString("address_2"), rs.getString("postal_code"), rs.getString("mobile"),
                    rs.getString("avarta"), createdAt, updatedAt);
        }, new Object[] { id });
        return Optional.ofNullable(profile);
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        Profile oldProfile = (Profile) this.read(id).orElseGet(() -> null);
        Profile newProfile = (Profile) object;
        Profile mainProfile = null;
        int rowsAffected = 0;

        if (oldProfile == null) {
            rowsAffected = this.insert(id, object);
        } else {
            
            mainProfile = new Profile(
                id, 
                
                newProfile.getFirstName(),

                newProfile.getLastName(), 

                newProfile.getGender(), 

                newProfile.getDob() != null ? newProfile.getDob().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null,

                newProfile.getNationality(), 

                newProfile.getCountryOfResidence(),

                newProfile.getCity(), 

                newProfile.getAddress1(),

                newProfile.getAddress2(), 

                newProfile.getPostalCode(),

                newProfile.getMobile(),

                newProfile.getAvarta(),

                null,

                LocalDateTime.now()
            );

            final String sql = "UPDATE profiles SET first_name=?, "
                            +"last_name=?, "
                            +"gender=?, "
                            +"dob=?, "
                            +"nationality=?, "
                            +"country_of_residence=?, "
                            +"city=?, "
                            +"address_1=?, "
                            +"address_2=?, "
                            +"postal_code=?, "
                            +"mobile=?, "
                            +"avarta=?, "
                            +"updated_at=? "
                            +"WHERE profile_id=?";

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainProfile.getFirstName(),
                mainProfile.getLastName(),
                mainProfile.getGender(),
                mainProfile.getDob(),
                mainProfile.getNationality(),
                mainProfile.getCountryOfResidence(),
                mainProfile.getCity(),
                mainProfile.getAddress1(),
                mainProfile.getAddress2(),
                mainProfile.getPostalCode(),
                mainProfile.getMobile(),
                mainProfile.getAvarta(),
                mainProfile.getUpdatedAt(),
                mainProfile.getId()
            });
        }

        return rowsAffected;
    }

}
