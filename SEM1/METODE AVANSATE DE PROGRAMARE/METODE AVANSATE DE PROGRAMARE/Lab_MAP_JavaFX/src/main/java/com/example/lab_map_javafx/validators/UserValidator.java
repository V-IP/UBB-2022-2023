package com.example.lab_map_javafx.validators;


import com.example.lab_map_javafx.domain.User;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ValidationException {
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty())
            throw new ValidationException("User invalid!");
        }
}