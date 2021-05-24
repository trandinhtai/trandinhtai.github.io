package com.example.usermange.service;

import com.example.usermange.model.dto.UserDto;
import com.example.usermange.model.request.CreateUserReq;
import com.example.usermange.model.request.UpdateUserReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
     List<UserDto> getListUser();

     UserDto getUserById(int id);

     List<UserDto> searchUser(String keyword);

     UserDto createUser(CreateUserReq req);

     UserDto updateUser(UpdateUserReq req, int id);

     boolean deleteUser(int id);
}
