package com.team.youarelikemetoo.alarmFeed.dto;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedComment;
import com.team.youarelikemetoo.user.dto.UserSimpleProfile;
import com.team.youarelikemetoo.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AlarmFeedCommentDTO {

    private Long id;
    private String content;

    private UserSimpleProfile userSimpleProfile;

    public AlarmFeedComment toEntity(UserEntity user, AlarmFeed alarmFeed){
        AlarmFeedComment alarmFeedComment = AlarmFeedComment.builder()
                .content(this.content)
                .alarmFeed(alarmFeed)
                .user(user)
                .build();
        return alarmFeedComment;
    }

    public static AlarmFeedCommentDTO fromEntity(AlarmFeedComment entity){
        AlarmFeedCommentDTO dto = new AlarmFeedCommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());

        return dto;
    }


}
