package com.Subodh.SchoolProject.repository;

import com.Subodh.SchoolProject.model.Contact;
import com.Subodh.SchoolProject.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles getByRoleName(String roleName);
}
