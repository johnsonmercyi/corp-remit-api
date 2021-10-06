package com.soft.cr.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Staff {
    private final UUID id, profileId, branchId, userId, roleId, departmentId;
    private final String firstname, lastname, gender, branch, business, username, email, role, roleType, department;
    private final LocalDateTime createdAt, updatedAt;
    
    /**
     * @param id
     * @param profileId
     * @param branchId
     * @param userId
     * @param roleId
     * @param departmentId
     * @param firstname
     * @param lastname
     * @param gender
     * @param branch
     * @param business
     * @param username
     * @param email
     * @param role
     * @param roleType
     * @param department
     * @param createdAt
     * @param updatedAt
     */
    public Staff(
        @JsonProperty("id") UUID id, 
        @JsonProperty("profile_id") UUID profileId, 
        @JsonProperty("branch_id") UUID branchId, 
        @JsonProperty("user_id") UUID userId, 
        @JsonProperty("role_id") UUID roleId, 
        @JsonProperty("department_id") UUID departmentId, 
        @JsonProperty("first_name") String firstname, 
        @JsonProperty("last_name") String lastname, 
        @JsonProperty("gender") String gender,
        @JsonProperty("branch") String branch,
        @JsonProperty("business") String business,
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("role") String role,
        @JsonProperty("role_type") String roleType,
        @JsonProperty("department") String department,
        @JsonProperty("created_at") LocalDateTime createdAt, 
        @JsonProperty("updated_at") LocalDateTime updatedAt ) {
            
        this.id = id;
        this.profileId = profileId;
        this.branchId = branchId;
        this.userId = userId;
        this.roleId = roleId;
        this.departmentId = departmentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.branch = branch;
        this.business = business;
        this.username = username;
        this.email = email;
        this.role = role;
        this.roleType = roleType;
        this.department = department;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    /**
     * @return the profileId
     */
    public UUID getProfileId() {
        return profileId;
    }
    /**
     * @return the branchId
     */
    public UUID getBranchId() {
        return branchId;
    }
    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }
    /**
     * @return the roleId
     */
    public UUID getRoleId() {
        return roleId;
    }
    /**
     * @return the departmentId
     */
    public UUID getDepartmentId() {
        return departmentId;
    }
    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }
    /**
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }
    /**
     * @return the business
     */
    public String getBusiness() {
        return business;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }
    /**
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }
    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }
    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /**
     * @return the updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    



}
