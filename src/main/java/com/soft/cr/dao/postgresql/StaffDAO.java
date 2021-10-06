package com.soft.cr.dao.postgresql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soft.cr.dao.DataAccessModel;
import com.soft.cr.model.Profile;
import com.soft.cr.model.Staff;
import com.soft.cr.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postgres_staff")
public class StaffDAO implements DataAccessModel{
    private JdbcTemplate jdbcTemplate;
    private ProfileService profileService;
    @Autowired
    public StaffDAO (JdbcTemplate jdbcTemplate, ProfileService profileService) {
        this.jdbcTemplate = jdbcTemplate;
        this.profileService = profileService;
    }

    @Override
    public int delete(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int insert(UUID id, Object object) throws Exception {
        Staff staff = (Staff) object;// cast this object
        UUID profileId = null;

        //create a profile
        profileId = profileService.insertAndReturnId(new Profile(
            null, 
            staff.getFirstname(), 
            staff.getLastname(),
            staff.getGender(), 
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

        if (profileId == null) return 0; //return if a profile wasn't created

        //everything is alright. Go ahead and save staff with profile id
        String sql = "INSERT INTO staff (staff_id, profile_id, branch_id, user_id, role_id, department_id, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?)";

        int rowsAffected = 0;
        try {

            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                id,
                profileId,
                staff.getBranchId(),
                staff.getUserId(),
                staff.getRoleId(),
                staff.getDepartmentId(),
                LocalDateTime.now(),
                LocalDateTime.now()
            });

        } catch (Exception ex) {
            //catch exceptions related to creating staff 
            profileService.delete(profileId);//delete profile if error.
            throw new Exception("Error creating staff!");// breaks code operation at this point
        }

        //delete profile if this staff is not successfully created
        if (rowsAffected < 1) return profileService.delete(profileId);

        return rowsAffected;
    }

    @Override
    public List<Object> read() throws Exception {
        final String sql = "SELECT staff.staff_id, staff.profile_id, staff.branch_id, staff.user_id, staff.role_id, staff.department_id, profiles.first_name, profiles.last_name, profiles.gender, branches.name AS branch, businesses.name AS business, users.username, users.email, roles.name AS role, roles.type AS role_type, departments.name AS department,staff.created_at, staff.updated_at FROM staff "
        +"INNER JOIN profiles ON staff.profile_id = profiles.profile_id "
        +"INNER JOIN branches ON staff.branch_id = branches.profile_id "
        +"INNER JOIN businesses ON branches.business_id = businesses.business_id "
        +"INNER JOIN users ON staff.user_id = users.user_id "
        +"INNER JOIN roles ON staff.role_id = roles.role_id "
        +"INNER JOIN departments ON staff.department_id = departments.department_id;";

        return jdbcTemplate.query(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Staff(
                UUID.fromString(rs.getString("user_id")), 
                UUID.fromString(rs.getString("profile_id")),
                UUID.fromString(rs.getString("branch_id")),
                UUID.fromString(rs.getString("user_id")),
                UUID.fromString(rs.getString("role_id")),
                UUID.fromString(rs.getString("department_id")),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"), 
                rs.getString("branch"),
                rs.getString("business"), 
                rs.getString("username"), 
                rs.getString("email"), 
                rs.getString("role"), 
                rs.getString("role_type"), 
                rs.getString("department"),
                createdAt,
                updatedAt
            );
        });
    }
    @Override
    public Optional<Object> read(UUID id) throws Exception {

        final String sql = "SELECT staff.staff_id, staff.profile_id, staff.branch_id, staff.user_id, staff.role_id, staff.department_id, profiles.first_name, profiles.last_name, profiles.gender, branches.name AS branch, businesses.name AS business, users.username, users.email, roles.name AS role, roles.type AS role_type, departments.name AS department,staff.created_at, staff.updated_at FROM staff "
        +"INNER JOIN profiles ON staff.profile_id = profiles.profile_id "
        +"INNER JOIN branches ON staff.branch_id = branches.profile_id "
        +"INNER JOIN businesses ON branches.business_id = businesses.business_id "
        +"INNER JOIN users ON staff.user_id = users.user_id "
        +"INNER JOIN roles ON staff.role_id = roles.role_id "
        +"INNER JOIN departments ON staff.department_id = departments.department_id WHERE staff.staff_id=?;";

        Staff staff = jdbcTemplate.queryForObject(sql, (rs, i) -> {

            LocalDateTime updatedAt = rs.getString("updated_at") != null ? updatedAt = LocalDateTime.parse(
                    rs.getString("updated_at").substring(0, rs.getString("updated_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            LocalDateTime createdAt = rs.getString("created_at") != null ? createdAt = LocalDateTime.parse(
                    rs.getString("created_at").substring(0, rs.getString("created_at").indexOf(".")),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

            return new Staff(
                UUID.fromString(rs.getString("user_id")), 
                UUID.fromString(rs.getString("profile_id")),
                UUID.fromString(rs.getString("branch_id")),
                UUID.fromString(rs.getString("user_id")),
                UUID.fromString(rs.getString("role_id")),
                UUID.fromString(rs.getString("department_id")),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"), 
                rs.getString("branch"),
                rs.getString("business"), 
                rs.getString("username"), 
                rs.getString("email"), 
                rs.getString("role"), 
                rs.getString("role_type"), 
                rs.getString("department"),
                createdAt,
                updatedAt
            );
        }, new Object[]{id});

        return Optional.ofNullable(staff);
    }
    @Override
    public int update(UUID id, Object object) throws Exception {

        Staff oldStaff = (Staff) this.read(id).orElseGet(() -> null);
        Staff newStaff = (Staff) object;
        Staff mainStaff = null;
        Profile mainProfile = null;
        int rowsAffected = 0;

        if (oldStaff == null) {
            rowsAffected = this.insert(id, object);//insert if user doesn't exist!
        } else {
            //else try updating user

            Profile tempProfile = (Profile) profileService.read(oldStaff.getProfileId()).orElse(null);
            if (tempProfile == null) return 0;

            mainProfile = new Profile(
                oldStaff.getProfileId(),
                newStaff.getFirstname(),
                newStaff.getLastname(),
                newStaff.getGender(), 
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
            int outCome = profileService.update(oldStaff.getProfileId(), mainProfile);
            if (outCome < 1) return 0;//return 0 if profile isn't updated for some reasons.

            mainStaff = new Staff(
                id,
                oldStaff.getProfileId(), 
                newStaff.getBranchId(),
                newStaff.getUserId(),
                newStaff.getRoleId(),
                newStaff.getDepartmentId(),

                null,null,null,null,null,null,null,null,null,null,null,
                
                LocalDateTime.now()
            );

            final String sql = "UPDATE staff SET "
                                +"profile_id=?, "
                                +"branch_id=?, "
                                +"user_id=?, "
                                +"role_id=?, "
                                +"department_id=?, "
                                +"updated_at=? "
                                +"WHERE staff_id=?";

            //update user finally
            //this code might need enclosing in a try and catch block
            rowsAffected = jdbcTemplate.update(sql, new Object[]{
                mainStaff.getProfileId(),
                mainStaff.getBranchId(),
                mainStaff.getUserId(),
                mainStaff.getRoleId(),
                mainStaff.getDepartmentId(),
                mainStaff.getUpdatedAt(),
                mainStaff.getId()
            });
        }

        return rowsAffected;
    }

    
}
