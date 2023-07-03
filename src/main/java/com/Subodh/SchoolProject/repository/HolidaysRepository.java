package com.Subodh.SchoolProject.repository;

import com.Subodh.SchoolProject.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidaysRepository extends CrudRepository<Holiday,String> {

//    @Autowired
//    private final JdbcTemplate jdbcTemplate;
//
//
//
//    @Autowired
//    public HolidaysRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Holiday> findAllHolidays() {
//        String sql = "SELECT * FROM HOLIDAYS";
//        var rowmapper = BeanPropertyRowMapper.newInstance(Holiday.class);
//        return jdbcTemplate.query(sql, rowmapper);
//    }
}
