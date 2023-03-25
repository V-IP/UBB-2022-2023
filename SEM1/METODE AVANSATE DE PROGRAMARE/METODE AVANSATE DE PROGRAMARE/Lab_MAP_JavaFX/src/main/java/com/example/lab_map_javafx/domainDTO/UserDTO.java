package com.example.lab_map_javafx.domainDTO;

import com.example.lab_map_javafx.domain.Entity;

import java.util.Objects;

public class UserDTO extends Entity<Long> {
    private String name;
    private String date;

    public UserDTO(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name) && Objects.equals(date, userDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, date);
    }
}
