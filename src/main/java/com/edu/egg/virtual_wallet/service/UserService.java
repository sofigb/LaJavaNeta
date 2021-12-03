package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.User;

public interface UserService {

    void createUser(User newUser);
    void deactivateUser(User deletedUser);
    void editUser(User updatedUser);
    User returnUser(Integer idUser);
    // AKA Check Login Details. I think we should use a separate class for Login.
}
