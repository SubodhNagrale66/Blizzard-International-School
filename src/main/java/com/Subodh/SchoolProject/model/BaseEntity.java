package com.Subodh.SchoolProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt","createdBy","updatedAt","updatedBy"})
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    // @JsonIgnore // for indivdual ignoring entity to not send as response to rest api get 
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    // @JsonIgnore
    private String createdBy;
    
    @LastModifiedDate
    @Column(insertable = false)
    // @JsonIgnore
    private LocalDateTime updatedAt;
    
    @LastModifiedBy
    @Column(insertable = false)
    // @JsonIgnore
    private String updatedBy;

}
