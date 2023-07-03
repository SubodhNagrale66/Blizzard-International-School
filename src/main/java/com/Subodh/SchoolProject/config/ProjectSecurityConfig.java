package com.Subodh.SchoolProject.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().ignoringRequestMatchers("/saveMsg").ignoringRequestMatchers("/public/**")
                .ignoringRequestMatchers("/api/**")// as rest api will be consumed by other servers
                // .ignoringRequestMatchers(PathRequest.toH2Console()) //as we moving from h2
                // database to mysql database
                .ignoringRequestMatchers("/data-api/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/dashboard").authenticated() // dashboard after successful login

                // accessible only to admin
                .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .requestMatchers("/home").permitAll()
                .requestMatchers("/holidays/**").permitAll()
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/saveMsg").permitAll()
                .requestMatchers("/closeMsg/**").hasRole("ADMIN")

                // for spring data rest view / hal explorer
                .requestMatchers("/data-api/**").authenticated()
                // .requestMatchers("/courseses/**").permitAll()
                // .requestMatchers("/contacts/**").permitAll()

                // .requestMatchers("/index").permitAll()
                // .requestMatchers("/courses").permitAll()
                // for login to access
                .requestMatchers("/courses").authenticated()
                .requestMatchers("/displayProfile").authenticated()
                .requestMatchers("/updateProfile").authenticated()
                .requestMatchers("/student/**").hasRole("STUDENT")

                // implementing rest services
                .requestMatchers("/api/**").authenticated()
            

                .requestMatchers("/about").permitAll()
                .requestMatchers("/assets/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()

                .requestMatchers("/public/**").permitAll() // for all public pages
                // .requestMatchers(PathRequest.toH2Console()).permitAll()

                .and().formLogin().loginPage("/login") // for custom login page
                // .defaultSuccessUrl("/dashboard") //page after successful login
                .successForwardUrl("/dashboard")// page after successful login
                .failureUrl("/login?error=true").permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().httpBasic(); // for rest api

        // http.headers().frameOptions().disable(); //used for h2 database
        // //permits all
        // http.authorizeHttpRequests().anyRequest().permitAll()
        // .and().formLogin().
        // and().httpBasic();

        // //deny all
        // http.authorizeHttpRequests().anyRequest().denyAll()
        // .and().formLogin()
        // .and().httpBasic();

        return http.build();

    }

    // Using Password Hashing using BCrypt Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Using Password Hashing using BCrypt Password Encoder

    // Credentials are now verified from PersonRepository and security package with
    // DB connection

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //
    // UserDetails admin = User.withDefaultPasswordEncoder()
    // .username("user")
    // .password("123456")
    // .roles("USER")
    // .build();
    // UserDetails user = User.withDefaultPasswordEncoder()
    // .username("admin")
    // .password("543210")
    // .roles("USER", "ADMIN")
    // .build();
    // return new InMemoryUserDetailsManager(user, admin);
    // }
    //

}