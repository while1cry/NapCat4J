package me.while1cry.napcat4j.entity.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OnlineStatus {

    public static final OnlineStatus ONLINE = new OnlineStatus(10, 0, 0);
    public static final OnlineStatus Q_WO_BA = new OnlineStatus(60, 0, 0);
    public static final OnlineStatus AWAY = new OnlineStatus(30, 0, 0);
    public static final OnlineStatus BUSY = new OnlineStatus(50, 0, 0);
    public static final OnlineStatus DO_NOT_DISTURB = new OnlineStatus(70, 0, 0);
    public static final OnlineStatus HIDDEN = new OnlineStatus(40, 0, 0);
    public static final OnlineStatus LISTENING_TO_MUSIC = new OnlineStatus(10, 1028, 0);
    public static final OnlineStatus CHUN_RI_LIMITED = new OnlineStatus(10, 2037, 0);
    public static final OnlineStatus TOGETHER_IN_YUANMENG = new OnlineStatus(10, 2025, 0);
    public static final OnlineStatus LOOKING_FOR_STAR_PARTNER = new OnlineStatus(10, 2026, 0);
    public static final OnlineStatus EMPTY = new OnlineStatus(10, 2014, 0);
    public static final OnlineStatus TODAY_WEATHER = new OnlineStatus(10, 1030, 0);
    public static final OnlineStatus CRASHED = new OnlineStatus(10, 2019, 0);
    public static final OnlineStatus LOVE_YOU = new OnlineStatus(10, 2006, 0);
    public static final OnlineStatus IN_LOVE = new OnlineStatus(10, 1051, 0);
    public static final OnlineStatus GOOD_LUCK_KOI = new OnlineStatus(10, 1071, 0);
    public static final OnlineStatus MERCURY_RETROGRADE_DISPELLED = new OnlineStatus(10, 1201, 0);
    public static final OnlineStatus HIGH_FLYING = new OnlineStatus(10, 1056, 0);
    public static final OnlineStatus FULL_OF_VITALITY = new OnlineStatus(10, 1058, 0);
    public static final OnlineStatus BABY_VERIFIED = new OnlineStatus(10, 1070, 0);
    public static final OnlineStatus DIFFICULT_TO_EXPLAIN = new OnlineStatus(10, 1063, 0);
    public static final OnlineStatus HARD_TO_BE_FOOLISH = new OnlineStatus(10, 2001, 0);
    public static final OnlineStatus EMO = new OnlineStatus(10, 1401, 0);
    public static final OnlineStatus SO_DIFFICULT = new OnlineStatus(10, 1062, 0);
    public static final OnlineStatus OPENED_UP = new OnlineStatus(10, 2013, 0);
    public static final OnlineStatus NOTHING_IS_WRONG = new OnlineStatus(10, 1052, 0);
    public static final OnlineStatus WANT_PEACE = new OnlineStatus(10, 1061, 0);
    public static final OnlineStatus LEISURELY = new OnlineStatus(10, 1059, 0);
    public static final OnlineStatus GO_TRAVELING = new OnlineStatus(10, 2015, 0);
    public static final OnlineStatus WEAK_SIGNAL = new OnlineStatus(10, 1011, 0);
    public static final OnlineStatus GOING_OUT_FOR_FUN = new OnlineStatus(10, 2003, 0);
    public static final OnlineStatus DOING_HOMEWORK = new OnlineStatus(10, 2012, 0);
    public static final OnlineStatus STUDYING = new OnlineStatus(10, 1018, 0);
    public static final OnlineStatus MOVING_BRICKS = new OnlineStatus(10, 2023, 0);
    public static final OnlineStatus FISHING = new OnlineStatus(10, 1300, 0);
    public static final OnlineStatus BORED = new OnlineStatus(10, 1060, 0);
    public static final OnlineStatus TIMI = new OnlineStatus(10, 1027, 0);
    public static final OnlineStatus SLEEPING = new OnlineStatus(10, 1016, 0);
    public static final OnlineStatus STAYING_UP = new OnlineStatus(10, 1032, 0);
    public static final OnlineStatus WATCHING_DRAMA = new OnlineStatus(10, 1021, 0);
    @JsonProperty("status")
    private final int status;
    @JsonProperty("ext_status")
    private final int extStatus;
    @JsonProperty("battery_status")
    private final int batteryStatus;

    public static OnlineStatus MY_BATTERY(int batteryStatus) {
        return new OnlineStatus(10, 1000, batteryStatus);
    }
}
