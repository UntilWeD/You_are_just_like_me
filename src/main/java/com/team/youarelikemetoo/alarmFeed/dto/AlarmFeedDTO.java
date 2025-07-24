package com.team.youarelikemetoo.alarmFeed.dto;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedImage;
import com.team.youarelikemetoo.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
public class AlarmFeedDTO {

    private int id;
    private List<AlarmFeedImage> imageList;
    private String feedContent;
    private int likeCount;
    private int shareCount;

    public AlarmFeed toEntity(UserEntity user){

    }

}
