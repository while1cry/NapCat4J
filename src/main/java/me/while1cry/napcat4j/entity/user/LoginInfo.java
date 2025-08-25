package me.while1cry.napcat4j.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginInfo {

    @JsonProperty("user_id")
    private final String userId;

    @JsonProperty("nickname")
    private final String nickname;
}
