package com.electronicStore.controller;

import com.electronicStore.Security.JwtHelper;
import com.electronicStore.dtos.JwtRequest;
import com.electronicStore.dtos.JwtResponse;
import com.electronicStore.dtos.UserDto;
import com.electronicStore.entities.User;
import com.electronicStore.exceptions.BadApiRequest;
import com.electronicStore.services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController" ,description = "API's for authentication")
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

    @Value("${newPassword}")
    private String newPassword;

    @Value("${googleClientId}")
    private  String googleClientId;


    private Logger logger = LoggerFactory.getLogger(AuthController.class);


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

    // login with google api
    @PostMapping("/google")
    public  ResponseEntity<JwtResponse> loginWithGoogle (@RequestBody Map<String,Object> data ) throws IOException {
        String idToken =data.get("idToken").toString();
      NetHttpTransport netHttpTransport=  new NetHttpTransport();
        JacksonFactory jacksonFactory =  new JacksonFactory();
        GoogleIdTokenVerifier.Builder  verifier=   new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));
        GoogleIdToken googleIdToken=GoogleIdToken.parse(verifier.getJsonFactory(),idToken );
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        logger.info("Payload : {}",payload);
        String email = payload.getEmail();
        User user= null;
        userService.findUserByEmailOptional(email).orElse(null);
        if(user==null){
           user= this.saveUser(email, data.get("name").toString(),data.get("photourl").toString());
        }
        ResponseEntity<JwtResponse> jwtResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
        return  jwtResponseEntity;

    }

    private User saveUser(String email, String name, String photourl) {
        UserDto userDto = UserDto.builder()
                .name(name)
                .email(email)
                .imageName(photourl)
                .password(newPassword)
                .roles(new HashSet<>())
                .build();
        UserDto userDtoRtn = userService.create(userDto);
        return modelMapper.map(userDtoRtn,User.class);

    }

}
