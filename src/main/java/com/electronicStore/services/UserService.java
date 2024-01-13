package com.electronicStore.services;

import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.UserDto;
import com.electronicStore.entities.User;
import com.electronicStore.repository.UserRepository;

import java.util.List;
import java.util.Optional;


public interface UserService {
    //create
    UserDto create(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser (String userId);

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize , String sortBy, String sortDir );

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String userId);

    List<UserDto> searchBy(String keyWord);

    // for checking google email

    Optional<User> findUserByEmailOptional(String email);




}
