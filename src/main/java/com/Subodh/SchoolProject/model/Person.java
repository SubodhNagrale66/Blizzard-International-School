package com.Subodh.SchoolProject.model;

import com.Subodh.SchoolProject.annotation.FieldsValueMatch;
import com.Subodh.SchoolProject.annotation.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;

// import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// @Data
@Getter
@Setter
@Entity
@FieldsValueMatch.List({
                @FieldsValueMatch(field = "pwd", fieldMatch = "confirmPwd", message = "Passwords do not match!"),
                @FieldsValueMatch(field = "email", fieldMatch = "confirmEmail", message = "Email addresses do not match!")
})
public class Person extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private int personId;

        @NotBlank(message = "Name must not be blank")
        @Size(min = 3, message = "Name must be at least 3 characters long")
        private String name;

        @NotBlank(message = "Mobile number must not be blank")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        private String mobileNumber;

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Please provide a valid email address")
        private String email;

        @NotBlank(message = "Confirm Email must not be blank")
        @Email(message = "Please provide a valid confirm email address")
        @Transient
        @JsonIgnore
        private String confirmEmail;

        @NotBlank(message = "Password must not be blank")
        @Size(min = 5, message = "Password must be at least 5 characters long")
        @PasswordValidator
        @JsonIgnore
        private String pwd;

        @NotBlank(message = "Confirm Password must not be blank")
        @Size(min = 5, message = "Confirm Password must be at least 5 characters long")
        @Transient
        @JsonIgnore
        private String confirmPwd;

        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
        @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false) // name=roles_id is from person
                                                                                         // table column and roleId is
                                                                                         // from Roles.class
        private Roles roles;

        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
        @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
        private Address address;

        @ManyToOne(fetch = FetchType.LAZY, optional = true)
        @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
        private SchoolClass schoolClass;

        // ManyTOMany
        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
        @JoinTable(name = "person_courses", joinColumns = {
                        @JoinColumn(name = "person_id", referencedColumnName = "personId") }, inverseJoinColumns = {
                                        @JoinColumn(name = "course_id", referencedColumnName = "courseId") })
        private Set<Courses> courses = new HashSet<>();

}
