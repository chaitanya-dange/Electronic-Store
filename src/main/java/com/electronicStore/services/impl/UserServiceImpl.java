package com.electronicStore.services.impl;

import com.electronicStore.dtos.UserDto;
import com.electronicStore.entities.User;
import com.electronicStore.repository.UserRepository;
import com.electronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ModelMapper modelMapper;
    @Override
    public UserDto create(UserDto userDto) {
        String userId= UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(savedUser, UserDto.class);

        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found exception"));
        User user01 = modelMapper.map(userDto, User.class);
        userRepository.save(user01);
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String userId) {
        return null;
    }

    @Override
    public List<UserDto> searchBy(String keyWord) {
        return null;
    }
}
