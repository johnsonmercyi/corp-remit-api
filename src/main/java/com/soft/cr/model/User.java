package com.soft.cr.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    
    private final UUID id;
    private final UUID profileId;
    private final String username;
    private final String password;
    private final String email;
    private final String token;
    private final UUID roleId;
    private final LocalDateTime lastLogin;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * @param id
     * @param profileId
     * @param username
     * @param password
     * @param email
     * @param token
     * @param roleId
     * @param lastLogin
     * @param createdAt
     * @param updatedAt
     */
    public User(@JsonProperty("id") UUID id, 
    @JsonProperty("profile_id") UUID profileId, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("email") String email, @JsonProperty("token") String token, @JsonProperty("role_id") UUID roleId, @JsonProperty("last_login") LocalDateTime lastLogin, @JsonProperty("created_at") LocalDateTime createdAt, @JsonProperty("updated_at") LocalDateTime updatedAt) {
        this.id = id;
        this.profileId = profileId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
        this.roleId = roleId;
        this.lastLogin = lastLogin;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the roleId
     */
    public UUID getRoleId() {
        return roleId;
    }

    /**
     * @return the lastLogin
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
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
