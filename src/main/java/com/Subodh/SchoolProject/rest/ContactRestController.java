package com.Subodh.SchoolProject.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Subodh.SchoolProject.constant.SchoolProjectConstants;
import com.Subodh.SchoolProject.model.Contact;
import com.Subodh.SchoolProject.model.Response;
import com.Subodh.SchoolProject.repository.ContactRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;

@Slf4j
// @Controller //if we use controller then we should use @ResponseBody
// annotation for all rest end point
@RestController // This resolve the use of @ResponseBody annotation over all rest end point
                // functions
@RequestMapping(path = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*") // for CORS Cross Origin Resource Sharing i.e Api's can be access by any host 
// @CrossOrigin(origins = "http:/localhost:8080") //for specifying api access
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    // @Autowired
    // Response response;

    @GetMapping("/getMessageByStatus")
    // @ResponseBody
    public List<Contact> getMessageByStatus(@RequestParam(name = "status") String status) {
        return contactRepository.findByStatus(status);

    }

    @GetMapping("/getAllMsgsByStatus")
    // @ResponseBody
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact) {
        if (null != contact && null != contact.getStatus()) {
            return contactRepository.findByStatus(contact.getStatus());
        } else {
            return List.of();
        }
    }

    @PostMapping("/saveMsg")
    // @ResponseBody
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
            @Valid @RequestBody Contact contact) {
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMsg") // for update msg status
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq) {
        Response response = new Response();
        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());
        if (contact.isPresent()) {
            contact.get().setStatus(SchoolProjectConstants.CLOSE);
            contactRepository.save(contact.get());
        } else {
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
