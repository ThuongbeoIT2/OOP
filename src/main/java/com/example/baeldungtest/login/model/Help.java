package com.example.baeldungtest.login.model;

import com.example.baeldungtest.Email.ValidEmail;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "help")
@Table
@Data
public class Help {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(unique = true)
    @ValidEmail
    private String email;
    @Column
    private String title;
    @ManyToOne
    @JoinColumn(name = "userID",foreignKey = @ForeignKey(name = "fk_help_user"))
    private User user;
}
