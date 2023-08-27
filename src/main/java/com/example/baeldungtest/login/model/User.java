package com.example.baeldungtest.login.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userID;
    @Column(name = "email")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide your first name")
    private String firstname;
    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastname;
    @Column(name = "enabled")
    private boolean enabled;

    public User() {
        super();
        this.enabled=false;

    }
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    private List<Help> helps;

}
