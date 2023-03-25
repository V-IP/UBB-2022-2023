package com.example.lab_map_javafx.repository;

import com.example.lab_map_javafx.domain.User;

import java.util.List;

public interface Repository<ID, E> {

    int size();

    void save(E entity);

    void delete(ID id);

    E findOneById(ID id);

    void update(E entity, E newEntity);

    List<E> findAll();
}
