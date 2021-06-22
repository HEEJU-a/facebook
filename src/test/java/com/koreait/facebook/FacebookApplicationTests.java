package com.koreait.facebook;

import com.koreait.facebook.common.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FacebookApplicationTests {

    @Autowired
    private EmailService email;
    @Test
    void sendEmail() {
        String to = "heejjuu9923@naver.com";
        String subject = "제목입니다";
        String txt = "내용입니다<a href=\"http://localhost:8090/user/login\">로그인으로이동</a>";
        email.sendMimeMessage(to, subject, txt);
    }

}
