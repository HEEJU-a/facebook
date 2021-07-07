package com.koreait.facebook.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDomain extends UserEntity{
    private int cntFeed;
    private int cntFollower; //팔로워
    private int cntFollow; // 팔로우
}
