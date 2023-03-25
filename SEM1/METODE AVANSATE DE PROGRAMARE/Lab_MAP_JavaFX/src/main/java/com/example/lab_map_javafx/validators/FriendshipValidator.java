package com.example.lab_map_javafx.validators;

import com.example.lab_map_javafx.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity.getId().getE1() == entity.getId().getE2()) throw new ValidationException("Invalid friendship!");
    }
}
