package com.jvfn.notes.entity;

import com.jvfn.notes.model.Note;
import com.jvfn.notes.model.User;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NoteDAO {
    Note getNoteById(int noteID);
    List<Note> getAllNote();
    List<Note> getNoteByUsername(String username);
    List<Note> findByTitle(String title, int userID);
    void deleteNote(int noteID);
    void updateNote(Note note);
    void createNote(Note note);
}
