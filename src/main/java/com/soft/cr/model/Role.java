package com.soft.cr.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
    
    private final UUID id;
    private final String desc;
    private final String type;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    
    /**
     * @param id
     * @param desc
     * @param type
     * @param createdAt
     * @param updatedAt
     */
    public Role(@JsonProperty("id") UUID id, @JsonProperty("desc") String desc, @JsonProperty("type") String type, @JsonProperty("created_at") LocalDateTime createdAt, @JsonProperty("updated_at") LocalDateTime updatedAt) {
        this.id = id;
        this.desc = desc;
        this.type = type;
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
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
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
