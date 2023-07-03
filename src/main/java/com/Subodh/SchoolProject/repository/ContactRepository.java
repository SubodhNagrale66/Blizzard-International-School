package com.Subodh.SchoolProject.repository;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Subodh.SchoolProject.model.Contact;

import jakarta.transaction.Transactional;

//import com.Subodh.SchoolProject.rowmappers.ContactRowMapper;

import java.util.List;

@Repository
// public interface ContactRepository extends CrudRepository<Contact, Integer> {
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);

    // Implementing Pagination and dynamic sorting
    // Page<Contact> findByStatus(String status, Pageable pageable);

    // Named Query JPQL
    @Query("SELECT c FROM Contact c WHERE c.status=:status") // :status for directly referring to argument ?1 for 1st
                                                             // argument
    Page<Contact> findByStatus(@Param("status") String status, Pageable pageable);

    // Native SQL Query
    // @Query(value = "SELECT * FROM contact_msg c WHERE c.status =
    // :status",nativeQuery = true) // :status for directly referring to argument ?1
    // for 1st argument
    // Page<Contact> findByStatus(String status, Pageable pageable);

    // updating msg status
    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

    // Named Queries from Contact Model Class
    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);
    // Named Queries from Contact Model Class

    // Named native queries
    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);
    // Named native queries


    //
    // private final JdbcTemplate jdbcTemplate;
    // @Autowired
    // public ContactRepository(JdbcTemplate jdbcTemplate){
    // this.jdbcTemplate=jdbcTemplate;
    // }
    //
    // public int saveContactMsg(Contact contact){
    // String sql = "INSERT INTO CONTACT_MSG
    // (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS," +
    // "CREATED_AT,CREATED_BY) VALUES (?,?,?,?,?,?,?,?)";
    // return jdbcTemplate.update(sql,contact.getName(),contact.getMobileNum(),
    // contact.getEmail(),contact.getSubject(),contact.getMessage(),
    // contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
    //
    // }
    //
    // public List<Contact> findMsgsWithOpenStatus(String status) {
    // String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
    // return jdbcTemplate.query(sql,new PreparedStatementSetter() {
    // public void setValues(PreparedStatement preparedStatement) throws
    // SQLException {
    // preparedStatement.setString(1, status);
    // }
    // },new ContactRowMapper());
    // }
    //
    // public int updateMsgStatus(int contactId, String status,String updatedBy) {
    // String sql = "UPDATE CONTACT_MSG SET STATUS = ?, UPDATED_BY = ?,UPDATED_AT =?
    // WHERE CONTACT_ID = ?";
    // return jdbcTemplate.update(sql,new PreparedStatementSetter() {
    // public void setValues(PreparedStatement preparedStatement) throws
    // SQLException {
    // preparedStatement.setString(1, status);
    // preparedStatement.setString(2, updatedBy);
    // preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
    // preparedStatement.setInt(4, contactId);
    // }
    // });
    // }

}
