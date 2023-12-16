package com.electronicStore.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //generating id's manually
    private  String userId;
    @Column(name = "user_name")
    private  String name;
    @Column(name = "user_email" ,unique = true)
    private String email;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_gender")
    private String gender;
    @Column(name = "user_about",length = 1000)
    private String    about;

    @Column(name = "user_image_name")
    private String imageName;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
}
