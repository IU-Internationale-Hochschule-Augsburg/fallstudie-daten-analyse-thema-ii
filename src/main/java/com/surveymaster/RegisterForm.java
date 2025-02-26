package com.surveymaster;

import lombok.Data;

@Data
public class RegisterForm {
    private String username;

    private String surname;

    private String firstname;

    private String email;

    private String password;

    private String confirmPassword;
}
