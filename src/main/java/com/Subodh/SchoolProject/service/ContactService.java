package com.Subodh.SchoolProject.service;

import com.Subodh.SchoolProject.constant.SchoolProjectConstants;
import com.Subodh.SchoolProject.model.Contact;
import com.Subodh.SchoolProject.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
//@RequestScope
//@SessionScope
@ApplicationScope
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactService() {
        System.out.println("ContactService bean is initialized");
    }


    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        //TODO - NEED TO PERSIST DATA into DB TABLE
        // DB storage CODE
        contact.setStatus(SchoolProjectConstants.OPEN);
//        contact.setCreatedAt(LocalDateTime.now());
//        contact.setCreatedBy(SchoolProjectConstants.ANONYMOUS);
//int result=contactRepository.saveContactMsg(contact);
        Contact savedContact = contactRepository.save(contact);

        if (null != savedContact && savedContact.getContactId() > 0) {
            isSaved = true;
        }

//        log.info(contact.toString());
        return isSaved;
    }

  public Page<Contact> findMsgsWithOpenStatus(int pageNum,String sortField, String sortDir){
        int pageSize = 5;
        
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        // Page<Contact> msgPage = contactRepository.findByStatus(
            Page<Contact> msgPage=contactRepository.findByStatus(
                SchoolProjectConstants.OPEN,pageable);
        return msgPage;
    }

    // public boolean updateMsgStatus(int contactId) {
            public boolean updateMsgStatus(int contactId) {
        boolean isUpdated = false;
        // Optional<Contact> contact = contactRepository.findById(contactId);
        // contact.ifPresent(contact1 -> {
        //     contact1.setStatus(SchoolProjectConstants.CLOSE);
//            contact1.setUpdatedBy(updatedBy);
//            contact1.setUpdatedAt(LocalDateTime.now());
        // });
        // int rows = contactRepository.updateStatusById(SchoolProjectConstants.CLOSE,contactId);
                int rows = contactRepository.updateMsgStatus(SchoolProjectConstants.CLOSE,contactId);

        if (rows > 0) {
            isUpdated = true;
        }

        return isUpdated;
    }


    
}
