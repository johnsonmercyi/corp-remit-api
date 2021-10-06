package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Profile;
import com.soft.cr.model.User;
import com.soft.cr.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_user")
public class UserDAO implements DataAccessModel {

    private final JdbcTemplate jdbcTemplate;
    private ProfileService profileService;

    /**
     * @param jdbcTemplate
     */
    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate, ProfileService profileService) {
        this.jdbcTemplate = jdbcTemplate;
        this.profileService = profileService;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT users.user_id, users.profile_id, username, password, email, remember_me_token, role_id, last_login, users.created_at, users.updated_at, profiles.first_name, profiles.last_name, profiles.gender FROM users "
        +"INNER JOIN profiles ON users.profile_id = profiles.profile_id WHERE users.status=?;";

        return jdbcTemplate.query(sql, (rs, i) -> {

            LocalDateTime lastLogin = rs.getString("last_login") != null ? LocalDateTime.parse(
                    rs.getString("last_login").substring(0, rs.getString("last_login").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new User(
                UUID.fromString(rs.getString("user_id")), 
                UUID.fromString(rs.getString("profile_id")),
                rs.getString("username"), 
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"), 
                rs.getString("email"),
                rs.getString("remember_me_token"), 
                rs.getString("role_id") != null ? UUID.fromString(rs.getString("role_id")) : null, 
                lastLogin, 
                createdAt,
                updatedAt
            );
        }, new Object[]{1});

    }

    @Override
    public Optional<Object> read(UUID id) throws Exception {

        final String sql = "SELECT users.user_id, users.profile_id, username, password, email, remember_me_token, role_id, last_login, users.created_at, users.updated_at, profiles.first_name, profiles.last_name, profiles.gender FROM users "
        +"INNER JOIN profiles ON users.profile_id = profiles.profile_id "
        +"WHERE user_id = ? AND users.status=?;";

        User user = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime lastLogin = rs.getString("last_login") != null ? LocalDateTime.parse(
                    rs.getString("last_login").substring(0, rs.getString("last_login").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new User(
                UUID.fromString(rs.getString("user_id")), 
                UUID.fromString(rs.getString("profile_id")),
                rs.getString("username"), rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"),
                rs.getString("email"),
                rs.getString("remember_me_token"), 
                rs.getString("role_id") != null ? UUID.fromString(rs.getString("role_id")) : null,
                lastLogin, 
                createdAt,
                updatedAt
            );
        }, new Object[] { id, 1 });

        return Optional.ofNullable(user);
    }

    @Override
    public int insert(UUID id, Object object) throws Exception {
        User user = (User) object;// cast this object
        UUID profileId = null;

        // if (user.getProfileId() != null)//check for existence of record
        //     temp = (Profile) profileService.read(user.getProfileId()).orElse(null);

        if (user.getProfileId() == null) {
            //create a profile
            profileId = profileService.insertAndReturnId(new Profile(
                null, 
                user.getFirstname(), 
                user.getLastname(),
                user.getGender(), 
                null, 
                null, 
                null, 
                null, 
                null, 
                null, 
                null, 
                null, 
                null, 
                LocalDateTime.now(), 
                LocalDateTime.now()
            ));
        } else {
            profileId = user.getProfileId();
        }

        if (profileId == null) return 0; //return if a profile wasn't created

        //everything is alright. Go ahead and save user with profile id
        String sql = "INSERT INTO users (user_id, profile_id, username, password, email, created_at, updated_at) VALUES (?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        try {

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                id,
                profileId,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                LocalDateTime.now(),
                LocalDateTime.now()
            });

        } catch (Exception ex) {
            //catch exceptions related to creating user 
            profileService.delete(profileId);//delete profile if error.
            throw new Exception("Error creating user!");// breaks code operation at this point
        }

        //delete profile if this user is not successfully created
        if (rowsAffected < 1) return profileService.delete(profileId);

        return rowsAffected;
    }

    @Override
    public int update(UUID id, Object object) throws Exception {
        User oldUser = (User) this.read(id).orElseGet(() -> null);
        User newUser = (User) object;
        User mainUser = null;
        Profile mainProfile = null;
        int rowsAffected = 0;

        if (oldUser == null) {
            rowsAffected = this.insert(id, object);//insert if user doesn't exist!
        } else {
            //else try updating user

            Profile tempProfile = (Profile) profileService.read(oldUser.getProfileId()).orElse(null);
            if (tempProfile == null) return 0;

            mainProfile = new Profile(
                oldUser.getProfileId(),
                newUser.getFirstname(),
                newUser.getLastname(),
                newUser.getGender(), 
                tempProfile.getDob() != null ? tempProfile.getDob().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null, 
                tempProfile.getNationality(), 
                tempProfile.getCountryOfResidence(), 
                tempProfile.getCity(), 
                tempProfile.getAddress1(), 
                tempProfile.getAddress2(), 
                tempProfile.getPostalCode(), 
                tempProfile.getMobile(), 
                tempProfile.getAvarta(), 
                tempProfile.getCreatedAt(), 
                LocalDateTime.now()
            );

            //update profile first
            int outCome = profileService.update(oldUser.getProfileId(), mainProfile);
            if (outCome < 1) return 0;//return 0 if profile isn't updated for some reasons.

            mainUser = new User(
                id,
                oldUser.getProfileId(), 
                
                newUser.getUsername(),

                newUser.getPassword(),

                null,null,null,

                newUser.getEmail(),

                oldUser.getToken(),

                newUser.getRoleId(),

                newUser.getLastLogin(),

                null,
                
                LocalDateTime.now()
            );

            final String sql = "UPDATE users SET "
                                +"username=?, "
                                +"password=?, "
                                +"email=?, "
                                +"remember_me_token=?, "
                                +"role_id=?, "
                                +"last_login=?, "
                                +"updated_at=? "
                                +"WHERE user_id=?";

            //update user finally
            //this code might need enclosing in a try and catch block
            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainUser.getUsername(),
                mainUser.getPassword(),
                mainUser.getEmail(),
                mainUser.getToken(),
                mainUser.getRoleId(),
                mainUser.getLastLogin(),
                mainUser.getUpdatedAt(),
                mainUser.getId()
            });
        }

        return rowsAffected;
    }

    @Override
    public int delete(UUID id) throws Exception {
        User user = (User) this.read(id).orElse(null);
        if (user == null) return 0;
        
        return jdbcTemplate.update("UPDATE users SET status=0 WHERE user_id=?", new Object[]{id});
    }

}
