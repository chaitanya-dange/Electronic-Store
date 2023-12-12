package com.electronicStore.services.impl;

import com.electronicStore.dtos.UserDto;
import com.electronicStore.entities.User;
import com.electronicStore.exceptions.ResourceNotFoundException;
import com.electronicStore.repository.UserRepository;
import com.electronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);


        return modelMapper.map(updatedUser,UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public List<UserDto> getAllUsers( int pageNumber, int pageSize ,String sortBy, String sortDir ) {
        Sort sort =   sortDir.equalsIgnoreCase("desc") ? ( Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending())  ;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pageUser= userRepository.findAll(pageable);
        List<User> userList = pageUser.getContent();
        List<UserDto> userDtosList= userList.stream().map(user ->  modelMapper.map(user,UserDto.class)).toList();
        return userDtosList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String userId) {
        User userByEmail = userRepository.findByEmail(userId).orElseThrow(() -> new ResourceNotFoundException("user not found."));
        return modelMapper.map(userByEmail,UserDto.class);
    }

    @Override
    public List<UserDto> searchBy(String keyWord) {
        List<User> userReceived = userRepository.findByNameContaining(keyWord);
        List<UserDto> listOfDto = userReceived.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return listOfDto;
    }
}
