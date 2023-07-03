package com.Subodh.SchoolProject.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int roleId;

    private String roleName;

}
