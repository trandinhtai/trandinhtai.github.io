package com.example.vonline.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserReq {
    private String fullName;

    private String email;

    private String password;

    private String phone;
}

