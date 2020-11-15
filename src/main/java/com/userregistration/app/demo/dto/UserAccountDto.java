package com.userregistration.app.demo.dto;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class UserAccountDto {
    private Long id;
    @NotNull
    @Length(min = 3, max = 16, message = "Length must be from 3 to 16")
    @Pattern(regexp = "^[a-zA-Z]+", message = "Only Latin letters")
    private String username;
    @NotNull
    @Length(min = 1, max = 16, message = "Length must be from 1 to 16")
    @Pattern(regexp = "^[a-zA-Z]+", message = "Only Latin letters")
    private String firstName;
    @NotNull
    @Length(min = 1, max = 16, message = "Length must be from 1 to 16")
    @Pattern(regexp = "^[a-zA-Z]+", message = "Only Latin letters")
    private String lastName;
    @NotNull
    @Size(min = 3, max = 16, message = "Length must be from 3 to 16")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)",
            message = "Only Latin letters and numbers, at least 1 number and 1 letter")
    private String password;
    @NotNull
    private String roleName;
    private String status;
    private LocalDateTime createdAt;

    public UserAccountDto() {
    }

    public UserAccountDto(Long id, String username, String firstName, String lastName,
                          String password, String roleName, String status,
                          LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roleName = roleName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
