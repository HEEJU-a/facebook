package com.koreait.facebook.feed;

import com.koreait.facebook.feed.model.FeedDomain;
import com.koreait.facebook.feed.model.FeedEntity;
import com.koreait.facebook.feed.model.FeedImgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedEntity param);
    int insFeedImg(FeedImgEntity param);
    List<FeedDomain> selFeedList();
}
