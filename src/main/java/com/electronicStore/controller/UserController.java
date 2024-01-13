package com.electronicStore.controller;

import com.electronicStore.dtos.ImageResponse;
import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.UserDto;
import com.electronicStore.services.FileService;
import com.electronicStore.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "UserController" ,description = "API's for User Module")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

   private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Value("${user.profile.image.path}")
    private  String imageUploadPath;

    //create
    @PostMapping()
    @Operation(summary = "create new User", description = "this is api for creating the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success | OK "),
            @ApiResponse(responseCode = "401", description = "not authorized !! "),
            @ApiResponse(responseCode = "201", description = "new user created !! ")
    })
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

        UserDto user = userService.getUserById(userId);
        userService.deleteUser(userId);

        String fullPath= imageUploadPath+user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);

        }catch (NoSuchFileException ex){
            logger.info("User image not found in folder");
            ex.printStackTrace();

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    //get single user by id
    @GetMapping("/{userId}")
    @Operation(summary = "Get single user by userId ")
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
    @Operation(summary = "get all users")
        public PageableResponse<UserDto> getAll(@RequestParam( value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
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

    // upload user image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,@PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        ImageResponse imageResp = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).build();
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        userService.updateUser(user,userId);

        return  new ResponseEntity<>(imageResp,HttpStatus.CREATED);
    }

    // serve user image
    @GetMapping("/image/{userId}")
    public  void serverUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());



    }
}
