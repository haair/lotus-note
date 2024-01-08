package com.jvfn.notes.entity;

import com.jvfn.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MailUserImp {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ALL = "SELECT * FROM MailUser";

//    @Override
    public User getUserById(int id) {
        return null;
    }

//    @Override
    public boolean isExistUser(String username, String password) {
        return false;
    }

//    @Override
    public List<User> getAllUser() {
        return jdbcTemplate.query(SQL_GET_ALL, BeanPropertyRowMapper.newInstance(User.class));
    }

//    @Override
    public void deleteUser(int userID) {

    }

//    @Override
    public void updateUser(User user) {

    }

//    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO MailUser (username, password, fullName, emailAddress)"
                + " VALUES (?, ?, ?, ?)";
//        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(),
//                user.getFullName(), user.getEmailAddress());
        System.out.println("create successfully");
    }
}
