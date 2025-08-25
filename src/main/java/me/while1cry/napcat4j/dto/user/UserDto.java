package me.while1cry.napcat4j.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("uin")
    private String uin;

    @JsonProperty("nick")
    private String nick;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("constellation")
    private int constellation;

    @JsonProperty("shengXiao")
    private int shengXiao;

    @JsonProperty("kBloodType")
    private int kBloodType;

    @JsonProperty("homeTown")
    private String homeTown;

    @JsonProperty("makeFriendCareer")
    private int makeFriendCareer;

    @JsonProperty("pos")
    private String pos;

    @JsonProperty("college")
    private String college;

    @JsonProperty("country")
    private String country;

    @JsonProperty("province")
    private String province;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("address")
    private String address;

    @JsonProperty("regTime")
    private long regTime;

    @JsonProperty("interest")
    private String interest;

    @JsonProperty("labels")
    private List<String> labels;

    @JsonProperty("qqLevel")
    private int qqLevel;

    @JsonProperty("qid")
    private String qid;

    @JsonProperty("longNick")
    private String longNick;

    @JsonProperty("birthday_year")
    private int birthdayYear;

    @JsonProperty("birthday_month")
    private int birthdayMonth;

    @JsonProperty("birthday_day")
    private int birthdayDay;

    @JsonProperty("age")
    private int age;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("eMail")
    private String eMail;

    @JsonProperty("phoneNum")
    private String phoneNum;

    @JsonProperty("categoryId")
    private int categoryId;

    @JsonProperty("richTime")
    private long richTime;

    @JsonProperty("richBuffer")
    private Map<String, Object> richBuffer;

    @JsonProperty("status")
    private int status;

    @JsonProperty("extStatus")
    private int extStatus;

    @JsonProperty("batteryStatus")
    private int batteryStatus;

    @JsonProperty("termType")
    private int termType;

    @JsonProperty("netType")
    private int netType;

    @JsonProperty("iconType")
    private int iconType;

    @JsonProperty("customStatus")
    private String customStatus;

    @JsonProperty("setTime")
    private String setTime;

    @JsonProperty("specialFlag")
    private int specialFlag;

    @JsonProperty("abiFlag")
    private int abiFlag;

    @JsonProperty("eNetworkType")
    private int eNetworkType;

    @JsonProperty("showName")
    private String showName;

    @JsonProperty("termDesc")
    private String termDesc;

    @JsonProperty("musicInfo")
    private MusicInfo musicInfo;

    @JsonProperty("extOnlineBusinessInfo")
    private ExtOnlineBusinessInfo extOnlineBusinessInfo;

    @JsonProperty("extBuffer")
    private ExtBuffer extBuffer;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("long_nick")
    private String long_nick;

    @JsonProperty("reg_time")
    private long reg_time;

    @JsonProperty("is_vip")
    private boolean isVip;

    @JsonProperty("is_years_vip")
    private boolean isYearsVip;

    @JsonProperty("vip_level")
    private int vipLevel;

    @JsonProperty("login_days")
    private int loginDays;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicInfo {
        @JsonProperty("buf")
        private Map<String, Object> buf;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtOnlineBusinessInfo {
        @JsonProperty("buf")
        private Map<String, Object> buf;

        @JsonProperty("customStatus")
        private String customStatus;

        @JsonProperty("videoBizInfo")
        private VideoBizInfo videoBizInfo;

        @JsonProperty("videoInfo")
        private VideoInfo videoInfo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoBizInfo {
        @JsonProperty("cid")
        private String cid;

        @JsonProperty("tvUrl")
        private String tvUrl;

        @JsonProperty("synchType")
        private String synchType;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoInfo {
        @JsonProperty("name")
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtBuffer {
        @JsonProperty("buf")
        private Map<String, Object> buf;
    }
}
