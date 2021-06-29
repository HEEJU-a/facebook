package com.koreait.facebook.feed.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedDomain extends FeedEntity{
    private String writer;
    private int ifeedimg;
    private String img;
    private String mainProfile;
}
