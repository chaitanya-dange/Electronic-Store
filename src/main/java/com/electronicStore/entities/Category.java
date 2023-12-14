package com.electronicStore.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;

    @Column(name = "category_title",length = 80 ,nullable = false)
    private String title;
    @Column(name = "category_description")
    private String description;

    private String coverImage;

    // other fields.
    @OneToMany(cascade =  CascadeType.ALL,fetch = FetchType.LAZY ,mappedBy = "category")
    private List<Product> products= new ArrayList<>();
}
