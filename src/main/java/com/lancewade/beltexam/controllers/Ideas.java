package com.lancewade.beltexam.controllers;

import com.lancewade.beltexam.models.Idea;
import com.lancewade.beltexam.models.User;
import com.lancewade.beltexam.services.IdeaService;
import com.lancewade.beltexam.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class Ideas {
    private final UserService userService;
    private final IdeaService ideaService;

    public Ideas(UserService userService, IdeaService ideaService) {
        this.userService = userService;
        this.ideaService = ideaService;
    }

    @GetMapping("/ideas")
    public String ideasPage(HttpSession session, Model model,
                             @ModelAttribute("idea") Idea idea) {
        Long userId = (Long) session.getAttribute("userId");
        User u = userService.findUserById(userId);
        model.addAttribute("user", u);
        model.addAttribute("ideas", ideaService.findAllIdeas());
        return "ideas.jsp";
    }

    @GetMapping("/ideas/new")
    public String newIdea(@ModelAttribute("idea") Idea idea,
                          Model model,
                          HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User u = userService.findUserById(userId);
        model.addAttribute("user", u);
        return "newIdea.jsp";
    }
    @PostMapping("/ideas/new")
    public String createIdea(@Valid @ModelAttribute("idea") Idea idea, BindingResult result){
        if(result.hasErrors()){
            return "newIdea.jsp";
        }
        else{
            ideaService.saveIdea(idea);
            return "redirect:/ideas/";
        }
    }
    @GetMapping("/ideas/{id}")
    public String viewIdea(@PathVariable("id") Long id,
                            Model model,
                           HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User u = userService.findUserById(userId);
        model.addAttribute("user", u);
        Idea idea = ideaService.findIdeaById(id);
        model.addAttribute("idea", idea);
        return "viewidea.jsp";
    }
    @GetMapping("/ideas/{id}/join")
    public String likeIdea(@PathVariable("id") Long id, HttpSession session) {
        User user = userService.findUserById((Long) session.getAttribute("userId"));
        Idea idea = ideaService.findIdeaById(id);
        System.out.println(idea.getUserLikes());
        List<User> likes = idea.getUserLikes();
        likes.add(user);
        idea.setUserLikes(likes);
        userService.saveUser(user);
        return "redirect:/ideas";
    }
    @GetMapping("/ideas/{id}/cancel")
    public String cancelEvent(@PathVariable("id") Long id, HttpSession session) {
        User user = userService.findUserById((Long) session.getAttribute("userId"));
        Idea idea = ideaService.findIdeaById(id);
        List<User> likes = idea.getUserLikes();
        for(int i=0; i<likes.size(); i++) {
            if(likes.get(i).getId() == user.getId()) {
                likes.remove(i);
            }
        }
        idea.setUserLikes(likes);
        userService.saveUser(user);
        return "redirect:/ideas";
    }

    @GetMapping("/ideas/{id}/edit")
    public String editPage(@PathVariable("id") Long id, @ModelAttribute("idea") Idea idea, Model model, HttpSession session) {
        if(session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        User user = userService.findUserById((Long)session.getAttribute("userId"));
        if(ideaService.findIdeaById(id).getCreator().getId() == user.getId()) {
            model.addAttribute("idea", ideaService.findIdeaById(id));
            return "edit.jsp";
        }
        else {
            return "redirect:/";
        }
    }

    @PutMapping("/ideas/{id}/edit")
    public String editIdea(@Valid @ModelAttribute("idea") Idea idea,
                            BindingResult result,@PathVariable("id") Long id,
                            Model model,
                            HttpSession session) {
        User user = userService.findUserById((Long)session.getAttribute("userId"));
        if(ideaService.findIdeaById(id).getCreator().getId() == user.getId()) {
            if(result.hasErrors()) {
//                model.addAttribute("event", eventService.findEventById(id));
                return "edit.jsp";
            }
            else {
                Idea ideaEdit = ideaService.findIdeaById(id);
                model.addAttribute("idea", ideaEdit);
                model.addAttribute("user", user);
                idea.setCreator(user);
                idea.setUserLikes(idea.getUserLikes());
                ideaService.saveIdea(idea);
                return "redirect:/ideas";
            }
        }
        else {
            return "redirect:/";
        }
    }
    @DeleteMapping("/ideas/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        Idea idea = ideaService.findIdeaById(id);
        for(User user: idea.getUserLikes()) {
            List<Idea> myIdeas = user.getIdeasLiked();
            myIdeas.remove(idea);
            user.setIdeasLiked(myIdeas);;
            userService.saveUser(user);
        }
        ideaService.deleteIdea(id);
        return "redirect:/ideas";
    }
}
