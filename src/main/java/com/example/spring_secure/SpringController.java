package com.example.spring_secure;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")

public class SpringController {

    private static final Logger logger = LoggerFactory.getLogger(SpringController.class);
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final ComponentClass componentClass;
    private final MaskingUtils maskingUtils;
    private final List<UserDTO> registerUsers = new ArrayList<>();




    public SpringController(InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder,ComponentClass componentClass, MaskingUtils maskingUtils) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.componentClass = componentClass;
        this.maskingUtils = maskingUtils;
    }



    @GetMapping("/")
    public String loggedIn() {
        logger.debug("huvudmeny på endpoint / ");

        return "LoggedIn";
    }

    @GetMapping("/perform_logout")
    public String loggingout() {
        logger.debug("utför utloggning med endpoint /perform_logout");

        return "perform_logout";
    }



    @GetMapping("/register")
    public String registerForm(Model model) {
        logger.debug("registrering av användare på endpoint /register");

        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            logger.warn("något saknas i skapandet: " + bindingResult.getAllErrors());
            logger.debug("något gick fel under registering");
            return "register";
        }

        String escapedEmail = HtmlUtils.htmlEscape(userDTO.getEmail());


        UserDetails toRegister = User.builder()
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .username(userDTO.getEmail())
                .roles("USER")
                .build();
        inMemoryUserDetailsManager.createUser(toRegister);

        String maskedEmail = maskingUtils.maskEmail(escapedEmail);

        registerUsers.add(userDTO);


        logger.info("Ny användare skapad: email: " + maskedEmail);
        logger.debug("avnändare registrerad " + maskedEmail);
        return "registerOK";
    }

    @GetMapping("/loggedout")
    public String loggedout(){

        return "loggedout";
    }

    @GetMapping ("/divide")
    public int divide(@RequestParam int a, @RequestParam int b){
       return componentClass.divide(a,b);
    }

    @GetMapping("/registeredUsers")
    public String registeredUsers(Model model){

        logger.debug("lista över registrerade användare på endpoint /registeredUsers");

        if(registerUsers.isEmpty()){
            logger.warn(("Inga användare har blivit registrerade"));
            logger.debug("listan med användare verkar vara tom");
        }

        model.addAttribute("users", registerUsers);
        return "registeredUsers";
    }

}
