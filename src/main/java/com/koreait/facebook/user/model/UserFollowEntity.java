package com.koreait.facebook.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserFollowEntity {
    private int iuserMe;
    private int iuserYou;
    private String regdt;
}
