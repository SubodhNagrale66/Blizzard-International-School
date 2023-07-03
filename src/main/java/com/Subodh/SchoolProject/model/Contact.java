package com.Subodh.SchoolProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "contact_msg")
@SqlResultSetMappings({
                @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})
@NamedQueries({
                @NamedQuery(name = "Contact.findOpenMsgs", query = "SELECT c FROM Contact c WHERE c.status = :status"),
                @NamedQuery(name = "Contact.updateMsgStatus", query = "UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
})

@NamedNativeQueries({
                @NamedNativeQuery(name = "Contact.findOpenMsgsNative", query = "SELECT * FROM contact_msg c WHERE c.status = :status", resultClass = Contact.class),
                @NamedNativeQuery(name = "Contact.findOpenMsgsNative.count", query = "select count(*) as cnt from contact_msg c where c.status = :status", resultSetMapping = "SqlResultSetMapping.count"),

                @NamedNativeQuery(name = "Contact.updateMsgStatusNative", query = "UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
})
public class Contact extends BaseEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        @Column(name = "contact_id")
        @JsonProperty("ID")// for aliasing data sent for security
        private int contactId;

        @NotBlank(message = "Name must not be blank")
        @Size(min = 3, message = "Name should be atleast 3 characters")
        private String name;
        
        @NotBlank(message = "Mobile Number must not be blank")
        @Pattern(regexp = "(^$|[0-9]{10})", message = " Mobile number should be of 10 digits")
        @Size(min = 10, max = 10, message = "Mobile number should be 10 digits only")
        private String mobileNum;
        
        @NotBlank(message = "Email should not be blank")
        @Email(message = "Please provide valid email id")
        private String email;
        
        @NotBlank(message = "Subject should not be blank")
        @Size(min = 5, message = "Subject required to be more than 5 characters")
        private String subject;
        
        @NotBlank(message = "Message should not be blank")
        @Size(min = 15, message = "Message required to be more than 15 characters")
        private String message;

        private String status;

}
