package me.while1cry.napcat4j.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {

    @JsonProperty("birthday_year")
    private int birthdayYear;

    @JsonProperty("birthday_month")
    private int birthdayMonth;

    @JsonProperty("birthday_day")
    private int birthdayDay;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("age")
    private int age;

    @JsonProperty("phone_num")
    private String phoneNum;

    @JsonProperty("email")
    private String email;

    @JsonProperty("category_id")
    private int categoryId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("level")
    private int level;
}
