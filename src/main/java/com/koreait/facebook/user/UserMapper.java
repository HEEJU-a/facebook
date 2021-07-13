package com.koreait.facebook.user;

import com.koreait.facebook.user.model.UserDomain;
import com.koreait.facebook.user.model.UserEntity;
import com.koreait.facebook.user.model.UserFollowEntity;
import com.koreait.facebook.user.model.UserProfileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int join(UserEntity param);
    int auth(UserEntity param);
    UserEntity selUser(UserEntity param);
    int updUser(UserEntity param);
    int updUserMainProfile(UserProfileEntity param);


    int insUserFollow(UserFollowEntity param);
    UserFollowEntity selUserFollow(UserFollowEntity param);
    int delUserFollow(UserFollowEntity param);
    List<UserDomain> selUserFollowList(UserFollowEntity param);
    List<UserDomain> selUserFollowerList(UserFollowEntity param);

}
