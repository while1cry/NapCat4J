package me.while1cry.napcat4j.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberDto {

    @JsonProperty("group_id")
    private long groupId;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("card")
    private String card;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("age")
    private int age;

    @JsonProperty("area")
    private String area;

    @JsonProperty("level")
    private String level;

    @JsonProperty("qq_level")
    private int qqLevel;

    @JsonProperty("join_time")
    private long joinTime;

    @JsonProperty("last_sent_time")
    private long lastSentTime;

    @JsonProperty("title_expire_time")
    private long titleExpireTime;

    @JsonProperty("unfriendly")
    private boolean unfriendly;

    @JsonProperty("card_changeable")
    private boolean cardChangeable;

    @JsonProperty("is_robot")
    private boolean isRobot;

    @JsonProperty("shut_up_timestamp")
    private long shutUpTimestamp;

    @JsonProperty("role")
    private String role;

    @JsonProperty("title")
    private String title;
}
