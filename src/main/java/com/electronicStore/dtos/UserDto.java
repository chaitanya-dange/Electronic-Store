package com.electronicStore.dtos;

import com.electronicStore.entities.Role;
import com.electronicStore.validate.ImageNameValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    @Schema( name = "username", accessMode = Schema.AccessMode.READ_ONLY, description = "user name of new user !!")
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
