package com.electronicStore.dtos;

import com.electronicStore.entities.Role;
import com.electronicStore.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;

    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" ,message = "Please give proper email.")
    @NotBlank(message = "Email is required.")
    private String email;

    private String password;

    private String gender;

    private String about;

    @ImageNameValid
    private String imageName;

    private Set<RoleDto> roles= new HashSet<>();
}
