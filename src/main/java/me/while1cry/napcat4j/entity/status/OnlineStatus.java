package me.while1cry.napcat4j.entity.status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnlineStatus {

    ONLINE(10, 0, 0),
    Q_WO_BA(60, 0, 0),
    AWAY(30, 0, 0),
    BUSY(50, 0, 0),
    DO_NOT_DISTURB(70, 0, 0),
    HIDDEN(40, 0, 0),
    LISTENING_TO_MUSIC(10, 1028, 0),
    CHUN_RI_LIMITED(10, 2037, 0),
    TOGETHER_IN_YUANMENG(10, 2025, 0),
    LOOKING_FOR_STAR_PARTNER(10, 2026, 0),
    EMPTY(10, 2014, 0),
    TODAY_WEATHER(10, 1030, 0),
    CRASHED(10, 2019, 0),
    LOVE_YOU(10, 2006, 0),
    IN_LOVE(10, 1051, 0),
    GOOD_LUCK_KOI(10, 1071, 0),
    MERCURY_RETROGRADE_DISPELLED(10, 1201, 0),
    HIGH_FLYING(10, 1056, 0),
    FULL_OF_VITALITY(10, 1058, 0),
    BABY_VERIFIED(10, 1070, 0),
    DIFFICULT_TO_EXPLAIN(10, 1063, 0),
    HARD_TO_BE_FOOLISH(10, 2001, 0),
    EMO(10, 1401, 0),
    SO_DIFFICULT(10, 1062, 0),
    OPENED_UP(10, 2013, 0),
    NOTHING_IS_WRONG(10, 1052, 0),
    WANT_PEACE(10, 1061, 0),
    LEISURELY(10, 1059, 0),
    GO_TRAVELING(10, 2015, 0),
    WEAK_SIGNAL(10, 1011, 0),
    GOING_OUT_FOR_FUN(10, 2003, 0),
    DOING_HOMEWORK(10, 2012, 0),
    STUDYING(10, 1018, 0),
    MOVING_BRICKS(10, 2023, 0),
    FISHING(10, 1300, 0),
    BORED(10, 1060, 0),
    TIMI(10, 1027, 0),
    SLEEPING(10, 1016, 0),
    STAYING_UP(10, 1032, 0),
    WATCHING_DRAMA(10, 1021, 0),
    A_LITTLE_COLD(10, 2050, 0),
    HELLO_JANUARY(10, 2053, 0);

    private final int status;
    private final int extStatus;
    private final int batteryStatus;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class MY_BATTERY {

        private final int status;
        private final int extStatus;
        private final int batteryStatus;

        public static MY_BATTERY of(int batteryStatus) {
            return new MY_BATTERY(10, 1000, batteryStatus);
        }
    }
}