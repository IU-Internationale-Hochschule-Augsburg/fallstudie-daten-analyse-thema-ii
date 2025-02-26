package com.surveymaster;

import com.surveymaster.entity.User;
import com.surveymaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/login")
    public String loadLoginScreen(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute(loginForm);
        return "loginScreen";
    }

    @GetMapping("/register")
    public String loadRegistration(Model model) {
        RegisterForm registerForm = new RegisterForm();
        model.addAttribute(registerForm);
        return "registerScreen";
    }

    @PostMapping("/save-registration")
    public String registerNewUser(Model model, @ModelAttribute RegisterForm registerForm) {
        var user = userRepository.findByUsername(registerForm.getUsername());
        var email = userRepository.findByEmail(registerForm.getEmail());

        if (user == null && email == null) {
            User newUser = new User();
            newUser.setUsername(registerForm.getUsername());
            newUser.setFirstname(registerForm.getFirstname());
            newUser.setSurname(registerForm.getSurname());
            newUser.setEmail(registerForm.getEmail());
            var encryptedPassword = BCrypt.hashpw(registerForm.getPassword(), BCrypt.gensalt());
            if (BCrypt.checkpw(registerForm.getConfirmPassword(), encryptedPassword)) {
                newUser.setPassword(encryptedPassword);
            } else {
                model.addAttribute(registerForm);
                model.addAttribute("errorMessage", "Die Passwörter stimmen nicht überein!");
                return "filledRegisterScreen";
            }

            userRepository.save(newUser);
            model.addAttribute(newUser);
        } else {
            model.addAttribute(registerForm);
            model.addAttribute("errorMessage", "Benutzername oder E-Mail ist schon vergeben!");
            return "filledRegisterScreen";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/user-actions")
    public String handleUserActions(@RequestParam("userActions") String userActions, Model model) {
        if(userActions.startsWith("logout,")) {
            model.addAttribute(userActions);
            return "redirect:/logout";
        } else if(userActions.startsWith("settings,")) {
            // TODO: Add case for settings
        }
        return "redirect:/survey-admin";
    }
}