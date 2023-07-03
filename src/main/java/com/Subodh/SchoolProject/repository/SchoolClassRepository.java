package com.Subodh.SchoolProject.repository;

import com.Subodh.SchoolProject.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Integer> {
//    SchoolClassRepository.deleteById(Integer);
}
