package com.surveymaster.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
// Renamed because 'User' is reserved
@Table(name = "SurveyUser")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(length = 256)
    private String username;

    @Column(length = 256)
    private String surname;

    @Column(length = 256)
    private String firstname;

    @Column(length = 256)
    private String email;

    // So far plaintext, still not certain
    @Column(length = 256)
    private String password;

    public User() {
    }
}