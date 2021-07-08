package com.koreait.facebook.user;

import com.koreait.facebook.user.model.UserDTO;
import com.koreait.facebook.user.model.UserEntity;
import com.koreait.facebook.user.model.UserDomain;
import com.koreait.facebook.user.model.UserProfileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserProfileMapper {
    int insUserProfile(UserProfileEntity param);
    List<UserProfileEntity> selUserProfileList(UserEntity param);
    UserDomain selUserProfile(UserDTO param);
}
