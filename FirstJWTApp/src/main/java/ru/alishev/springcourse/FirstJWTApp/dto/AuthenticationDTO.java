package ru.alishev.springcourse.FirstJWTApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationDTO {
    @NotEmpty(message = "Name sholdn't be empty")
    @Size(message = "Имя должно быть от 2 до 100 символов длиной")
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotEmpty(message = "Name sholdn't be empty") @Size(message = "Имя должно быть от 2 до 100 символов длиной") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Name sholdn't be empty") @Size(message = "Имя должно быть от 2 до 100 символов длиной") String username) {
        this.username = username;
    }
}
