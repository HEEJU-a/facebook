package com.koreait.facebook.feed;

import com.koreait.facebook.common.MyFileUtils;
import com.koreait.facebook.feed.model.*;
import com.koreait.facebook.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    private FeedMapper mapper;

    @Autowired
    private IAuthenticationFacade auth;

    @Autowired
    private MyFileUtils myFileUtils;


    public int reg(MultipartFile[] imgArr, FeedEntity param){
        if(imgArr == null && param.getCtnt() == null){
            return 0;
        }
        param.setIuser(auth.getLoginUserPk());

        //t_feed
        int result = mapper.insFeed(param);
        if(param.getIfeed() > 0 && imgArr != null && imgArr.length > 0){//등록이 잘되었음

            FeedImgEntity param2 = new FeedImgEntity();
            param2.setIfeed(param.getIfeed());

            //이미지 저장
            String target = "feed/" + param.getIfeed();
            for(MultipartFile img : imgArr){
                String saveFileNm = myFileUtils.transferTo(img, target);

                if(saveFileNm != null){ //이미지 정보 db에 저장
                    param2.setImg(saveFileNm);
                    mapper.insFeedImg(param2);
                }
            }
        }
        return result;
    }
    public List<FeedDomain> selFeedList(){
       // List<FeedDomain2> list = mapper.selFeedList2();
        return mapper.selFeedList();
    }

    public List<FeedDomain2> selFeedList2(FeedDTO param){
        return mapper.selFeedList2(param);
    }
}
