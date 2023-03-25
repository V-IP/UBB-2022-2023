package com.example.lab_map_javafx.validators;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}