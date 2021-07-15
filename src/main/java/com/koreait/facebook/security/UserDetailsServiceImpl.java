package com.koreait.facebook.security;

import com.koreait.facebook.user.UserMapper;
import com.koreait.facebook.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return new CustomUserPrincipal(loadUserByUsernameAndProvider(email, "local"));
    }

    public UserEntity loadUserByUsernameAndProvider(String id, String provider) throws UsernameNotFoundException {
        UserEntity param = new UserEntity();
        param.setProvider(provider);
        param.setEmail(id);
        return  mapper.selUser(param);
    }

    public int join(UserEntity param) {
        if(param == null) {
            return 0;
        }
        return mapper.join(param);
    }



//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity param = new UserEntity();
//        param.setEmail(email);
//        UserEntity loginUser = mapper.selUser(param);
//        if(loginUser == null){
//            return null; //아이디가 없는 상태
//        }
//        return new CustomUserPrincipal(loginUser);//로그인 성공상태는 아닌데 아이디는 있는상태
//    }
}
