package com.koreait.facebook.feed.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedDTO {
    private int page;
    private int limit;
    private int iuser;

    public int getStartIdx(){
        return (page-1) * limit;
    }
}
