package com.jvfn.notes.entity;

import com.jvfn.notes.model.User;

import java.util.List;

public interface UserDAO {
    User getUserById(int userID);
    int getUserIdByUsername(String username);

    User isExistUser(String username, String password);

    List<User> getAllUser();

    void deleteUser(int userID);

    void updateUser(User user);

    void createUser(User user);
}
