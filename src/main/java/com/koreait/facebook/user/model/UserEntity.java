package com.koreait.facebook.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private int iuser;
    private String email;
    private String pw;
    private String nm;
    private String tel;
    private String authCd;
    private String regdt;
}
