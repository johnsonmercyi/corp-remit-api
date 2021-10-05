package com.soft.cr.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Business {
    
    private final UUID id;
    private final UUID user_id;
    private final String name;
    private final String rc_no;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;
    
    /**
     * @param id
     * @param name
     * @param rc_no
     * @param created_at
     * @param updated_at
     */
    public Business(@JsonProperty("id") UUID id, @JsonProperty("user_id") UUID user_id, @JsonProperty("name") String name, @JsonProperty("rc_no") String rc_no, @JsonProperty("created_at") LocalDateTime created_at, @JsonProperty("updated_at") LocalDateTime updated_at) {

        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.rc_no = rc_no;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    /**
     * @return the user_id
     */
    public UUID getUserId() {
        return user_id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the rc_no
     */
    public String getRcNo() {
        return rc_no;
    }
    /**
     * @return the created_at
     */
    public LocalDateTime getCreatedAt() {
        return created_at;
    }
    /**
     * @return the updated_at
     */
    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }
    

}
