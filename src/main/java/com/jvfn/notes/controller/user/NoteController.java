package com.jvfn.notes.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvfn.notes.entity.NoteDAO;
import com.jvfn.notes.entity.UserDAO;
import com.jvfn.notes.model.Note;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class NoteController {
    @Autowired
    private NoteDAO noteDAO;

    @GetMapping("/new-note")
    public String newNote() {
        return "user/note/new_note";
    }

    @PostMapping("/handle-save-note")
    public String saveNote(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession httpSession,
            Model model
    ) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String timeNow = now.format(formatter);

        Note note = new Note(-1, (int)httpSession.getAttribute("userID"), title, content, timeNow, timeNow);
        noteDAO.createNote(note);
        model.addAttribute("success", true);

        return "user/note/new_note";
    }

    @PostMapping("/handle-edit-note")
    public String editNote(
            @RequestParam("noteID") int noteID,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession httpSession
    ) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String timeNow = now.format(formatter);

        Note note = new Note(noteID, -1, title, content, null, timeNow);
        noteDAO.updateNote(note);
        System.out.println("edit ok!");

        return "redirect:/";
    }

    @RequestMapping("/note/delete/{noteID}")
    public String deleteNote(@PathVariable(name = "noteID") int noteID) {
        noteDAO.deleteNote(noteID);
        return "redirect:/";
    }

    @RequestMapping("/note/edit/{noteID}")
    public String editNote(@PathVariable(name = "noteID") int noteID,
                           Model model) {
        model.addAttribute("note", noteDAO.getNoteById(noteID));
        return "user/note/edit_note";
    }

    @RequestMapping("/search")
    public String searchPerson(Model model,
                               @Param("keyword") String keyword,
                               HttpSession httpSession) {
        List<Note> noteList = noteDAO.findByTitle(keyword, (int)httpSession.getAttribute("userID"));
        model.addAttribute("keyword", keyword);
        model.addAttribute("noteList", noteList);
        model.addAttribute("username", httpSession.getAttribute("username"));
        return "user/home/index";
    }
}
