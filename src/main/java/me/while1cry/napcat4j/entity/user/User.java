package me.while1cry.napcat4j.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String uid;
    private String uin;
    private String nick;
    private String remark;
    private int constellation;
    private int shengXiao;
    private int kBloodType;
    private String homeTown;
    private int makeFriendCareer;
    private String pos;
    private String college;
    private String country;
    private String province;
    private String city;
    private String postCode;
    private String address;
    private int regTime;
    private String interest;
    private String[] labels;
    private int qqLevel;
    private String qid;
    private String longNick;

    @JsonProperty("birthday_year")
    private int birthdayYear;

    @JsonProperty("birthday_month")
    private int birthdayMonth;

    @JsonProperty("birthday_day")
    private int birthdayDay;

    private int age;
    private String sex;
    private String eMail;
    private String phoneNum;
    private int categoryId;
    private int richTime;
    private JsonNode richBuffer;
    private int status;
    private int extStatus;
    private int batteryStatus;
    private int termType;
    private int netType;
    private int iconType;
    private @Nullable Object customStatus;
    private String setTime;
    private int specialFlag;
    private int abiFlag;
    private int eNetworkType;
    private String showName;
    private String termDesc;
    private JsonNode musicInfo;
    private JsonNode extOnlineBusinessInfo;
    private JsonNode extBuffer;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("long_nick")
    private String longNick_;

    @JsonProperty("reg_time")
    private int regTime_;

    @JsonProperty("is_vip")
    private boolean isVip;

    @JsonProperty("is_years_vip")
    private boolean isYearsVip;

    @JsonProperty("vip_level")
    private int vipLevel;

    @JsonProperty("login_days")
    private int loginDays;
}