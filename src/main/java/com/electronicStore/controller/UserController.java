package com.electronicStore.controller;

import com.electronicStore.dtos.UserDto;
import com.electronicStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //create
    @PostMapping()
    public UserDto createUser(@RequestBody UserDto userDto){
        UserDto userDtoRec = userService.create(userDto);
        return userDtoRec;
    }

    //update
    @PutMapping ("update/{id}")
    public  UserDto updateUser(@RequestBody UserDto userDto, @PathVariable String id){
        UserDto usrDtoRec = userService.updateUser(userDto, id);
        return usrDtoRec;
    }


    //delete
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }

    //get single user by id
    @GetMapping("/{userId}")
    public  UserDto getSingleUser (@PathVariable String userId){
        UserDto userDto = userService.getUserById(userId);
        return  userDto;
    }

    // get user by email
    @GetMapping("/email/{email}")
    public UserDto userByEmail ( @PathVariable String email){
        UserDto userDto = userService.getUserByEmail(email);
        return  userDto;
    }

    //get all users
    @GetMapping()
    public List<UserDto> getAll(@RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
                                @RequestParam( value = "sortBy",defaultValue ="name",required = false) String sortBy,
                                @RequestParam( value = "sortDir",defaultValue ="asc",required = false) String sortDir){
        return userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
    }


    // search user
    @GetMapping("/search/{keyword}")
    public  List<UserDto> search(@PathVariable String keyword){
        List<UserDto> userDtos = userService.searchBy(keyword);
        return  userDtos;
    }
}
