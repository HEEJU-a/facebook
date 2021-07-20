package com.koreait.facebook.security.model;

import com.koreait.facebook.user.model.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomUserPrincipal implements UserDetails, OAuth2User {


    @Getter
    private UserEntity user;
    private Map<String, Object> attributes;

    public CustomUserPrincipal(UserEntity user){
        this.user = user;
    }

    public CustomUserPrincipal(UserEntity user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPw();
    }

    @Override
    public String getUsername() {

        return user.getEmail();
    }
//    public String getNm(){
//         return user.getNm();
//    }
//
//    public UserEntity getUser(){
//        return user;
//    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }

    @Override
    public String getName(){
        return String.valueOf(user.getIuser());
    }
}
