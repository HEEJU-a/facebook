package com.koreait.facebook.user;

import com.koreait.facebook.common.EmailService;
import com.koreait.facebook.common.MyConst;
import com.koreait.facebook.common.MyFileUtils;
import com.koreait.facebook.common.MySecurityUtils;
import com.koreait.facebook.feed.FeedMapper;
import com.koreait.facebook.feed.model.FeedDTO;
import com.koreait.facebook.feed.model.FeedDomain2;
import com.koreait.facebook.security.IAuthenticationFacade;
import com.koreait.facebook.security.model.UserDetailsServiceImpl;
import com.koreait.facebook.user.model.*;
//mport org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private MyConst myConst;

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    public int join(UserEntity param){

        String authCd = secUtils.getRandomDigit(5);
        //비밀번호 암호화
      //  String hashedPw = BCrypt.hashpw(param.getPw(), BCrypt.gensalt());
        String hashedPw = passwordEncoder.encode(param.getPw());
        param.setPw(hashedPw);
        param.setAuthCd(authCd);
        param.setProvider("local");
        int result = userDetailService.join(param);

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
    public UserDomain selUserProfile(UserDTO param){
        param.setMeIuser(auth.getLoginUserPk());
        return userprofilemapper.selUserProfile(param);
    }

    public List<UserProfileEntity> selUserProfileList(UserEntity param){
        return userprofilemapper.selUserProfileList(param);
    }

    //메인이미지 변경
    public Map<String, Object> updUserMainProfile(UserProfileEntity param){
        UserEntity loginUser = auth.getLoginUser();
        param.setIuser(loginUser.getIuser());
        int result =  mapper.updUserMainProfile(param);
        if(result == 1){ //시큐리티 세션에 있는 loginUser에 있는 mainProfile값도 변경해주어야함
            loginUser.setMainProfile(param.getImg());

        }
        Map<String, Object> res = new HashMap<>();
        res.put("result", result);
        res.put("img", param.getImg());
        return res;
    }



    //팔로우 하기
    public Map<String, Object> insUserFollow(UserFollowEntity param){
        param.setIuserMe(auth.getLoginUserPk());
        Map<String, Object> res = new HashMap<>();
        res.put(myConst.RESULT, mapper.insUserFollow(param));
        return res;
    }
    public List<FeedDomain2> selFeedList2(FeedDTO param){

        return feedMapper.selFeedList2(param);
    }
    public List<UserDomain> selUserFollowList(UserFollowEntity param){
        param.setIuserMe(auth.getLoginUserPk());
        return mapper.selUserFollowList(param);
    }

    public List<UserDomain> selUserFollowerList(UserFollowEntity param) {
        param.setIuserMe(auth.getLoginUserPk());
        return mapper.selUserFollowerList(param);
    }


    //팔로우 취소
    public Map<String, Object> delUserFollow(UserFollowEntity param){
        param.setIuserMe(auth.getLoginUserPk());
        int result = mapper.delUserFollow(param); // 여기서 결과물이 있었으면 1일것임
                                                   // 여기서는 거의 0이 날아올수 없음! ->만약 0이 날아왔다면 에러!!

        Map<String, Object> res = new HashMap<>();
        res.put(myConst.RESULT, result);
        if(result == 1){
            UserFollowEntity param2 = new UserFollowEntity();
            param2.setIuserMe(param.getIuserYou());
            param2.setIuserYou(param.getIuserMe());

            UserFollowEntity result2 = mapper.selUserFollow(param2);
            res.put(myConst.YOU_FOLLOW_ME, result2);
        }
        return res;
    }



}
