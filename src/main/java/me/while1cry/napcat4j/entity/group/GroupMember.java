package me.while1cry.napcat4j.entity.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @JsonProperty("group_id")
    private String groupId;

    @JsonProperty("user_id")
    private String userId;

    private String nickname;
    private String card;
    private String sex;
    private int age;
    private String area;
    private int level;

    @JsonProperty("join_time")
    private int joinTime;

    @JsonProperty("last_sent_time")
    private int lastSentTime;

    @JsonProperty("title_expire_time")
    private int titleExpireTime;

    private boolean unfriendly;

    @JsonProperty("card_changeable")
    private boolean cardChangeable;

    @JsonProperty("is_robot")
    private boolean robot;

    @JsonProperty("shut_up_timestamp")
    private long shutUpTimestamp;

    private String role;
    private String title;
}
