package com.koreait.facebook.security;

import com.koreait.facebook.user.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements IAuthenticationFacade{

    @Override
    public UserEntity getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal userDetails= (CustomUserPrincipal) auth.getPrincipal();

        return userDetails.getUser();
    }

    @Override
    public int getLoginUserPk(){
//        PrintStream ddd = System.out;
//        ddd.println();
//        System.out.println();

//        UserEntity ue = getLoginUser();
//        ue.getIuser();
        return getLoginUser().getIuser();
    }
}
