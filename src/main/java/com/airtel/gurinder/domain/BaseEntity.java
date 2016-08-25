package com.airtel.gurinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by gurinder on 9/7/16.
 */
@Setter
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date updatedAt;

    @Column(name = "status")
    @JsonIgnore
    private boolean status;

    @PrePersist
    public void preCreate() {
        Date date = new Date();
        this.setCreatedAt(date);
    }

    @PreUpdate
    public void preUpdate() {
        Date date = new Date();
        this.setUpdatedAt(date);
    }


}
