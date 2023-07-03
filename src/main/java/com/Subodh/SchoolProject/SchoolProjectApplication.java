 	package com.Subodh.SchoolProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication //(exclude={DataSourceAutoConfiguration.class})
@EnableJpaRepositories("com.Subodh.SchoolProject.repository")
@EntityScan("com.Subodh.SchoolProject.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchoolProjectApplication.class, args);
	}

}
