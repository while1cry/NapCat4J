package me.while1cry.napcat4j.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @JsonProperty("birthday_year")
    private int birthdayYear;

    @JsonProperty("birthday_month")
    private int birthdayMonth;

    @JsonProperty("birthday_day")
    private int birthdayDay;

    @JsonProperty("user_id")
    private String userId;

    private int age;

    @JsonProperty("phone_num")
    private String phoneNum;

    private String email;

    @JsonProperty("category_id")
    private int categoryId;

    private String nickname;
    private String remark;
    private String sex;
    private int level;
}
