package com.Subodh.SchoolProject.repository;
import java.util.List;
// import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.Subodh.SchoolProject.model.Courses;

@Repository
@RepositoryRestResource(path= "courses")
// @RepositoryRestResource(exported = false) // for not exposing the rest api
public interface CoursesRepository extends JpaRepository<Courses,Integer>
{
    
    List<Courses> findByOrderByNameDesc();
    List<Courses> findByOrderByName();
}
