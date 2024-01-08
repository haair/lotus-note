package com.jvfn.notes.services;

import com.jvfn.notes.entity.NoteDAO;
import com.jvfn.notes.model.Note;
import com.jvfn.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteService implements NoteDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SQL_GET_ALL = "SELECT * FROM [Note]";
    @Override
    public Note getNoteById(int noteID) {
        String SQL = "SELECT * FROM Note WHERE noteID = " + noteID;
        List<Note> note = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Note.class));
        System.out.println(note.getFirst().getContent());
        return note.getFirst();
    }

    @Override
    public List<Note> getAllNote() {
        return jdbcTemplate.query(SQL_GET_ALL, BeanPropertyRowMapper.newInstance(Note.class));
    }

    @Override
    public List<Note> getNoteByUsername(String username) {
        String SQL = "SELECT noteID, u.userID, title, content, createdAt, lastModifiedAt FROM Note n INNER JOIN [User] u ON n.userID = u.userID WHERE u.username = '" + username + "' ORDER BY createdAt DESC";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Note.class));
    }

    @Override
    public void deleteNote(int noteID) {
        String SQL = "DELETE FROM Note WHERE noteID = ?";
        jdbcTemplate.update(SQL, noteID);
        System.out.println("Del ok!");
    }

    @Override
    public void updateNote(Note note) {
        String SQL = "UPDATE Note SET title = ?, content = ?, lastModifiedAt = ? WHERE noteID = ?";
        jdbcTemplate.update(SQL, note.getTitle(), note.getContent(), note.getLastModifiedAt(), note.getNoteID());
        System.out.println("update ok!");
    }

    @Override
    public void createNote(Note note) {
        String SQL = "INSERT INTO Note (userID, title, content, createdAt, lastModifiedAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, note.getUserID(), note.getTitle(), note.getContent(), note.getCreatedAt(), note.getLastModifiedAt());
        System.out.println("Add ok!");
    }
}
