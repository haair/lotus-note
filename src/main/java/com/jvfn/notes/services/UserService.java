package com.jvfn.notes.services;

import com.jvfn.notes.entity.UserDAO;
import com.jvfn.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SQL_GET_ALL = "SELECT * FROM [User]";
    @Override
    public User getUserById(int userID) {
        String SQL = "SELECT * FROM User WHERE userID = " + userID;
        return (User) jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public int getUserIdByUsername(String username) {
        return 0;
    }

    @Override
    public User isExistUser(String username, String password) {
        String SQL = "SELECT * FROM [User] WHERE username = '" + username + "' AND password = '" + password + "'";
        System.out.println(SQL);
        List<User> users = jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));
        if (users.size() > 0)
            return users.getFirst();
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return jdbcTemplate.query(SQL_GET_ALL, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public void deleteUser(int userID) {

    }

    @Override
    public void updateUser(User user) {
        String SQL = "UPDATE [User] SET password = ? WHERE userID = ?";
        jdbcTemplate.update(SQL, user.getPassword(), user.getUserID());
    }

    @Override
    public void createUser(User user) {

    }
}
