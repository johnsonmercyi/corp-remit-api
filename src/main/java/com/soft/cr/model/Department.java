package com.soft.cr.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Department {
    private final UUID id;
    private final String name;
    private final UUID branchId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    /**
     * @param id
     * @param name
     * @param branchId
     * @param createdAt
     * @param updatedAt
     */
    public Department(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("branch_id") UUID branchId, @JsonProperty("created_at") LocalDateTime createdAt, @JsonProperty("updated_at") LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.branchId = branchId;
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
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the branchId
     */
    public UUID getBranchId() {
        return branchId;
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
