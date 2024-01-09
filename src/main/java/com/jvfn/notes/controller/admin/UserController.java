package com.jvfn.notes.controller.admin;

import com.jvfn.notes.entity.UserDAO;
import com.jvfn.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserDAO userDAO;
    @RequestMapping("/admin/edit/user/{userID}")
    public String editUser(@PathVariable(name = "userID") int userID,
                           Model model) {
        model.addAttribute("note", userDAO.getUserById(userID));
        return "admin/user/edit_user";
    }

    @RequestMapping("/admin/delete/user/{userID}")
    public String deleteUser(@PathVariable(name = "userID") int userID) {
        userDAO.deleteUser(userID);
        return "redirect:/";
    }

    @RequestMapping("/admin/add/user")
    public String viewAddUser() {
        return "admin/user/add_user";
    }

    @RequestMapping("/handle-admin-add-user")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") int role
    ) {
        userDAO.createUser(new User(-1, username, password, role));
        return "redirect:/";
    }

    @PostMapping("/register")
    public String addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        userDAO.createUser(new User(-1, username, password, 1));
        System.out.println("register ok!");
        return "redirect:/";
    }


}
