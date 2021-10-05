package com.soft.cr.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Branch {
    private final UUID id;
    private final String name;
    private final String address;
    private final UUID businessId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * @param id
     * @param name
     * @param address
     * @param businessId
     * @param createdAt
     * @param updatedAt
     */
    public Branch (@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("address") String address, @JsonProperty("business_id") UUID businessId, @JsonProperty("created_at") LocalDateTime createdAt, @JsonProperty("updated_at") LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.businessId = businessId;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the businessId
     */
    public UUID getBusinessId() {
        return businessId;
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
