package com.koreait.facebook.user;

import com.koreait.facebook.common.EmailService;
import com.koreait.facebook.common.EmailServiceImpl;
import com.koreait.facebook.common.MyFileUtils;
import com.koreait.facebook.common.MySecurityUtils;
import com.koreait.facebook.security.IAuthenticationFacade;
import com.koreait.facebook.user.model.UserEntity;
//mport org.mindrot.jbcrypt.BCrypt;
import com.koreait.facebook.user.model.UserProfileEntity;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private EmailService email;

    @Autowired
    private MySecurityUtils secUtils;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthenticationFacade auth;

    @Autowired
    private MyFileUtils myFileUtils;

    @Autowired
    private UserProfileMapper userprofilemapper;

    public int join(UserEntity param){

        String authCd = secUtils.getRandomDigit(5);
        //비밀번호 암호화
      //  String hashedPw = BCrypt.hashpw(param.getPw(), BCrypt.gensalt());
        String hashedPw = passwordEncoder.encode(param.getPw());
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

//    //로그인
//    public String login(UserEntity param){
//        UserEntity loginUser = mapper.selUser(param);
//        return"";
//    }
    public void profileImg(MultipartFile[] imgArr){
        UserEntity loginUser = auth.getLoginUser();
        int iuser = loginUser.getIuser();
       // int iuser = auth.getLoginUserPk();
       // System.out.println("iuser : " + iuser);
        String target = "profile/" + iuser;
    //    System.out.println(imgArr);

        UserProfileEntity param = new UserProfileEntity();
        param.setIuser(iuser);
        for(MultipartFile img : imgArr){
            String saveFileNm = myFileUtils.transferTo(img, target);//sdhflksjdksjd.jpg
            //saveFileNm이 null이 아니라면 t_user_profile 테이블에
            //insert를 해주세요
            if(saveFileNm != null){
                param.setImg(saveFileNm);

                int result = userprofilemapper.insUserProfile(param);

                if(loginUser.getMainProfile() == null && result == 1){
                    UserEntity param2 = new UserEntity();
                    param2.setIuser(iuser);//8
                    param2.setMainProfile(saveFileNm); //sdhflksjdksjd.jpg

                   if(mapper.updUser(param2) == 1){
                       loginUser.setMainProfile(saveFileNm);
                   }
                }
            }
        }
    }
    public List<UserProfileEntity> selUserProfileList(UserEntity param){
        return userprofilemapper.selUserProfileList(param);
    }

    //메인이미지 변경
    public int updUserMainProfile(UserProfileEntity param){
        param.setIuser(auth.getLoginUserPk());
        return mapper.updUserMainProfile(param);
    }
}
