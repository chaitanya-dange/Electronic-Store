package com.electronicStore.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

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

    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Cart cart;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> roles= new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> collectedAuthorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
        return collectedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
   public String getPassword(){
        return  this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
