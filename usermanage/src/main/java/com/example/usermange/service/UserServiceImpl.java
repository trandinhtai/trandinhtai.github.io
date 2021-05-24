package com.example.usermange.service;

import com.example.usermange.entity.User;
import com.example.usermange.entity.UserRepository;
import com.example.usermange.exception.NotFoundException;
import com.example.usermange.model.dto.UserDto;
import com.example.usermange.model.mapper.UserMapper;
import com.example.usermange.model.request.CreateUserReq;
import com.example.usermange.model.request.UpdateUserReq;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserRepository userRepository;

    private static ArrayList<User> users = new ArrayList<User>();
/*    static {
        users.add(new User(1,"Tran Dinh Tai","trandinhtai@gmail.com","0982491985","HAT","123456a@"));
        users.add(new User(2,"Tran Dinh Tai1","trandinhtai1@gmail.com","0982491986","HAT","123456a@"));
    }*/

    @Override
    public List<UserDto> getListUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> result = new ArrayList<UserDto>();
        for(User user: users){
            result.add(UserMapper.toUserDto(user));

        }
        return result;
    }

    @Override
    public UserDto getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("No user found");
        }
        return UserMapper.toUserDto(user.get());
        /*for(User user: users){
            if (user.getId()==id){
                return UserMapper.toUserDto(user);
            }
        }*/
      /*  throw new NotFoundException("No user found");*/
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<UserDto> result = new ArrayList<>();
        for (User user: users){
            if (user.getFullname().contains(keyword)){
                result.add(UserMapper.toUserDto(user));
            }
        }
        return result;
    }

    @Override
    public UserDto createUser(CreateUserReq req) {
        // Convert CreateUserReq -> User
        User user = new User();
        user.setId(users.size()+1);
        user.setPhone(req.getPhone());
        user.setFullname(req.getName());
        user.setEmail(req.getEmail());
        // Mã hóa mật khẩu sử dụng BCrypt
        /*user.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12)));*/

        // Thêm user vào mảng
        /*users.add(user);*/
        userRepository.save(user);

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UpdateUserReq req, int id) {
        for (User user : users) {
            if (user.getId() == id) {
                if (!user.getEmail().equals(req.getEmail())) {
                    // Check new email exist
                    for (User tmp : users) {
                        if (tmp.getEmail().equals(req.getEmail())) {
                            throw new DuplicateRequestException("New email already exists in the system");
                        }
                    }
                    user.setEmail(req.getEmail());
                }
                user.setFullname(req.getName());
                user.setPhone(req.getPhone());
                user.setAvatar(req.getAvatar());
                /*user.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12)));*/
                return UserMapper.toUserDto(user);
            }
        }

        throw new NotFoundException("No user found");
    }

    @Override
    public boolean deleteUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                return true;
            }
        }

        throw new NotFoundException("No user found");
    }
}
