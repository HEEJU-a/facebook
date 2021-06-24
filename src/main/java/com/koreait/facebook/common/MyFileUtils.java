package com.koreait.facebook.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class MyFileUtils {

    @Value("${spring.servlet.multipart.location}")
    private String uploadImagePath;

    //폴더만들기
    public void makeFolders(String path) {
        File folder = new File(path);
        folder.mkdirs();
    }

    //저장 경로 만들기
    public String getSavePath(String path) {
        return uploadImagePath + "/" + path;
        // uploadImagePath  여기 들어있는 값은 C:/SpringImg
        //return
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();
    }

    //랜덤 파일명 만들기 (with 확장자)
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + "." + getExt(originFileNm);
        //return "dfkja;dkfj;aiej2" + "." + jpg
        //return "dfkja;dkfj;aiej2.jpg"
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm(MultipartFile file) {
        return getRandomFileNm(file.getOriginalFilename());
    }

    //확장자 얻기                 "aaa.jpg"
    public String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf(".") + 1);
    }

    //파일 저장 & 랜덤파일명 리턴
    public String transferTo(MultipartFile mf, String target) {
        String fileNm = getRandomFileNm(mf); //"dfkja;dkfj;aiej2.jpg//여기서 mf보낸건 jpg때문
        String basePath = getSavePath(target); //이미지를 저장할 절대경로값을 만든다 target="profile/10"
        //"C:/springImg/profile/10"
        System.out.println("basepath : "+basePath);
        makeFolders(basePath); //(폴더가 없을 수 있기 때문에) 폴더를 만들어준다
        File file = new File(basePath, fileNm);
        try {
            // File file = new File(basePath, fileNm); ->밖에 있어도 안에 있어도 상관없다
            mf.transferTo(file);
            return fileNm;
        } catch (Exception e) {
            e.printStackTrace();
            return null; //null이면 db에 저장 안하게끔끔        }

        }
    }
}
