package com.electronicStore.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.aspectj.weaver.ast.Not;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Min(value = 4,message = " Minimum should be of 4 character")
    private String title;
    @NotBlank(message = "Description should not be blank")
    private String description;

    private String coverImage;

}
