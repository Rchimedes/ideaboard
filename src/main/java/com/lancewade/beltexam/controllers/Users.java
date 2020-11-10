package com.lancewade.beltexam.controllers;

import com.lancewade.beltexam.models.User;
import com.lancewade.beltexam.services.UserService;
import com.lancewade.beltexam.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class Users {
    private final UserService userService;
    private final UserValidator userValidator;

    public Users(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String index(@ModelAttribute("user") User user) {
        return "index.jsp";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        // if result has errors, return the registration page (don't worry about validations just now)
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "index.jsp";
        } else {

                User u = userService.registerUser(user);
                session.setAttribute("userId", u.getId());
                return "redirect:/ideas";

        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
                            Model model, HttpSession session, @ModelAttribute("user") User user) {
        // if the user is authenticated, save their user id in session
        // else, add error messages and return the login page
        boolean isAuthenticated = userService.authenticateUser(email, password);
        if (isAuthenticated) {
            User u = userService.findByEmail(email);
            session.setAttribute("userId", u.getId());
            return "redirect:/ideas";
        } else {
            model.addAttribute("error", "Invalid credentials, please try again.");
            return "index.jsp";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
        // redirect to login page
        session.invalidate();
        return "redirect:/";
    }
}