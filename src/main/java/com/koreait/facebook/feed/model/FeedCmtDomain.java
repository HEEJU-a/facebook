package com.koreait.facebook.feed.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedCmtDomain extends FeedCmtEntity{
    private String writer;
    private String writerProfile;
    private int isMore;// 1:댓글 더 없음, 2: 댓글 더 있음
}
