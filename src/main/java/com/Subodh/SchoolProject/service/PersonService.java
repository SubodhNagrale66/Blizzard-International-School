package com.Subodh.SchoolProject.service;

import com.Subodh.SchoolProject.constant.SchoolProjectConstants;
import com.Subodh.SchoolProject.model.Person;
import com.Subodh.SchoolProject.model.Roles;
import com.Subodh.SchoolProject.repository.PersonRepository;
import com.Subodh.SchoolProject.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Autowired
    private PasswordEncoder passwordEncoder; //for Encoding password while saving

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(SchoolProjectConstants.STUDENT_ROLE);
        person.setRoles(role); // mandatory as its One to One object Relationship mapping i.e ORM mapping
        person.setPwd(passwordEncoder.encode(person.getPwd()));// Encoding password using Bcrypt Password Encoder
        person = personRepository.save(person); //saving new person in repository/DB
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
}
