package com.koreait.facebook.user;

import com.koreait.facebook.common.EmailService;
import com.koreait.facebook.common.EmailServiceImpl;
import com.koreait.facebook.common.MySecurityUtils;
import com.koreait.facebook.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmailService email;

    @Autowired
    private MySecurityUtils secUtils;

    @Autowired
    private UserMapper mapper;

    public int join(UserEntity param){

        String authCd = secUtils.getRandomDigit(5);
        //비밀번호 암호화
        String hashedPw = BCrypt.hashpw(param.getPw(), BCrypt.gensalt());
        param.setPw(hashedPw);
        param.setAuthCd(authCd);
        int result =  mapper.join(param);

        if(result == 1){//메일쏘기 (id, authcd값을 메일로 쏜다)
            String subject = "인증메일입니다";
            String txt =String.format("<a href=\"http://localhost:8090/user/auth?email=%s&authCd=%s\">인증하기</a>"
                        , param.getEmail(), authCd);
                    email.sendMimeMessage(param.getEmail(), subject, txt);
        }
        return result;
    }

    //이메일 인증처리
    public int auth(UserEntity param){
        return mapper.auth(param);
    }

//    public void sendEmail(){
//        String to = "heejjuu9923@naver.com";
//        String subject = "제목입니다";
//        String txt = "내용입니다";
//        email.sendSimpleMessage(to, subject, txt);
//
//    }
}
