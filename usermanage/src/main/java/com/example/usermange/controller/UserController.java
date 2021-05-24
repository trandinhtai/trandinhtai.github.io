package com.example.usermange.controller;

import com.example.usermange.exception.NotFoundException;
import com.example.usermange.form.UploadForm;
import com.example.usermange.model.dto.UserDto;
import com.example.usermange.model.request.CreateUserReq;
import com.example.usermange.model.request.UpdateUserReq;
import com.example.usermange.security.AuthenticateReq;
import com.example.usermange.security.JwtTokenUtil;
import com.example.usermange.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final String UPLOAD_DIR = System.getProperty("user.home") + "/upload";
    @Autowired
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticateReq req, HttpSession session){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("VIETTEL_SESSION",authentication.getName());
        return ResponseEntity.ok("Dang nhap thanh cong");
    }
    @PostMapping("/authentication")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticateReq req) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Gen token
        String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(token);
    }
    @ApiOperation(value = "Get list user", response = UserDto.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code=500,message = "")
    })
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        return ResponseEntity.ok("Profile");
    }
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/blog")
    public ResponseEntity<?> blog() {
        return ResponseEntity.ok("Blog");
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/shop")
    public ResponseEntity<?> shop() {
        return ResponseEntity.ok("Shop");
    }
    @GetMapping("")
    public ResponseEntity<?> getListUser(){
        List<UserDto> users = userService.getListUser();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @ApiOperation(value = "Get user info by id", response = UserDto.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "No user found"),
            @ApiResponse(code=500,message = "")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam(defaultValue = "",required = false) String name){
        List<UserDto> users = userService.searchUser(name);
        return ResponseEntity.ok(users);
    }
    @ApiOperation(value = "Create user", response = UserDto.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Email already exists in the system"),
            @ApiResponse(code=500,message = "")
    })
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid  @RequestBody CreateUserReq req){
        UserDto result = userService.createUser(req);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@ModelAttribute("uploadForm") UploadForm form){
        System.out.println(UPLOAD_DIR);
        File uploadDir = new File(UPLOAD_DIR);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        MultipartFile fileData = form.getFileData();
        String name = fileData.getOriginalFilename();
        if (name != null && name.length() > 0) {
            try {
                File serverFile = new File(UPLOAD_DIR + "/" + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(fileData.getBytes());
                stream.close();
                return ResponseEntity.ok(UPLOAD_DIR + "/" + name);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }   catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when uploading");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }
    @GetMapping("/file/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new NotFoundException("File not found");
        }

        UrlResource resource;
        try {
            resource = new UrlResource(file.toURI());
        } catch (MalformedURLException e) {
            throw new NotFoundException("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserReq req, @PathVariable int id) {
        UserDto result = userService.updateUser(req, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete success");
    }
}
