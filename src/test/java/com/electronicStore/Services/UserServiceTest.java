package com.electronicStore.Services;

import com.electronicStore.dtos.UserDto;
import com.electronicStore.entities.Role;
import com.electronicStore.entities.User;
import com.electronicStore.repository.RoleRepository;
import com.electronicStore.repository.UserRepository;
import com.electronicStore.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

//@ExtendWith(MockitoExtension.class)

@SpringBootTest
public class UserServiceTest {

    //@Mock
    @MockBean
    private RoleRepository roleRepository;
    //@Mock
    @MockBean
    private UserRepository userRepository;

    //@InjectMocks
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    String roleId;

    User user;
    Role role;
    @BeforeEach
    public  void init(){
        role=Role.builder().roleId("abcde").roleName("NORMAL").build();
        user=User.builder()
                .name("charlie")
                .email("charlie@gmail.com")
                .gender("Male")
                .imageName("ch.png")
                .password("lcwd")
                .roles(Set.of(role))
                .build();

        roleId="abcde";



    }

    @Test
    public  void createUserTest(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(roleRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(role));

           UserDto userDto=userService.create(modelMapper.map(user, UserDto.class));

        Assertions.assertNotNull(userDto);




    }

}
