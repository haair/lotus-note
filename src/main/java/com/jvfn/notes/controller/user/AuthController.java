package com.jvfn.notes.controller.user;

import com.jvfn.notes.entity.NoteDAO;
import com.jvfn.notes.entity.UserDAO;
import com.jvfn.notes.model.MailUser;
import com.jvfn.notes.model.Note;
import com.jvfn.notes.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private NoteDAO noteDAO;

    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullname") String fullName,
            @RequestParam("email") String email,
            Model model) {

        MailUser mailUser = new MailUser(-1, username, password, fullName, email);
//        mailUserDAO.createUser(mailUser);
        model.addAttribute("user", mailUser);

        return "info";
    }

    @PostMapping("/register1")
    public String handleRegistration(@ModelAttribute("user") MailUser registration, Model model) {
        model.addAttribute("user", registration);
        return "info";
    }

    @GetMapping("/form_info")
    public String formInfo(Model model) {
        List<User> users = userDAO.getAllUser();
        users.forEach(System.out :: println);
        model.addAttribute("customers", users);
        return "user/form_info";
    }

    @GetMapping("/")
    public String home(HttpSession httpSession, Model model) {
        String username = (String) httpSession.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
            List<Note> noteList = noteDAO.getNoteByUsername(username);
            model.addAttribute("noteList", noteList);
            return "user/home/index";
        }
        return "user/auth/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession httpSession
    ) {
        User user = userDAO.isExistUser(username, password);
        if (user != null) {
            httpSession.setAttribute("userID", user.getUserID());
            httpSession.setAttribute("username", username);
            httpSession.setAttribute("password", password);
            model.addAttribute("username", username);
            return "redirect:/";
        }
        model.addAttribute("unsuccess", true);
        return "user/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("userID");
        httpSession.removeAttribute("username");
        httpSession.removeAttribute("password");
        return "redirect:/";
    }

    @GetMapping("/change-password")
    public String requestChangePassword() {
        return "user/auth/change_password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("curr_pass") String curr_pass,
            @RequestParam("new_pass") String new_pass,
            @RequestParam("re_new_pass") String re_new_pass,
            HttpSession httpSession,
            Model model
    ) {
        System.out.println(new_pass + "|" + re_new_pass);
        if (curr_pass.equals(httpSession.getAttribute("password"))) {
            if (new_pass.equals(re_new_pass)) {
                User user = new User((int)httpSession.getAttribute("userID"), (String)httpSession.getAttribute("username"), new_pass, -1);
                userDAO.updateUser(user);
                model.addAttribute("STATUS", "Change password successful");
                httpSession.setAttribute("password", new_pass);
            }
            else {
                model.addAttribute("STATUS", "Retype new password not correct!");
            }
        } else {
            model.addAttribute("STATUS", "Current password not correct!");
        }
        return "user/auth/change_password";
    }



    @GetMapping("/form")
    public String form() {
        return "user/form";
    }
}
