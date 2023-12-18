package com.electronicStore.controller;

import com.electronicStore.Security.JwtHelper;
import com.electronicStore.dtos.JwtRequest;
import com.electronicStore.dtos.JwtResponse;
import com.electronicStore.dtos.UserDto;
import com.electronicStore.exceptions.BadApiRequest;
import com.electronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto)
                .build();

        return  new ResponseEntity<>(response,HttpStatus.OK);

    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email,password);
       try{
           manager.authenticate(authenticationToken);

       }catch (BadCredentialsException ex){
           throw new BadApiRequest("Invalid username or password ");

       }


    }


    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal  principal){
        String name = principal.getName();
        return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

}
