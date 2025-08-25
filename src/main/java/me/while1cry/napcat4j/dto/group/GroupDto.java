package me.while1cry.napcat4j.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    @JsonProperty("groupCode")
    private String groupCode;

    @JsonProperty("groupUin")
    private String groupUin;

    @JsonProperty("ownerUid")
    private String ownerUid;

    @JsonProperty("ownerUin")
    private String ownerUin;

    @JsonProperty("groupFlag")
    private long groupFlag;

    @JsonProperty("groupFlagExt")
    private long groupFlagExt;

    @JsonProperty("maxMemberNum")
    private int maxMemberNum;

    @JsonProperty("memberNum")
    private int memberNum;

    @JsonProperty("groupOption")
    private int groupOption;

    @JsonProperty("classExt")
    private int classExt;

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("fingerMemo")
    private String fingerMemo;

    @JsonProperty("groupQuestion")
    private String groupQuestion;

    @JsonProperty("certType")
    private int certType;

    @JsonProperty("richFingerMemo")
    private String richFingerMemo;

    @JsonProperty("tagRecord")
    private List<String> tagRecord;

    @JsonProperty("shutUpAllTimestamp")
    private long shutUpAllTimestamp;

    @JsonProperty("shutUpMeTimestamp")
    private long shutUpMeTimestamp;

    @JsonProperty("groupTypeFlag")
    private int groupTypeFlag;

    @JsonProperty("privilegeFlag")
    private long privilegeFlag;

    @JsonProperty("groupSecLevel")
    private int groupSecLevel;

    @JsonProperty("groupFlagExt3")
    private long groupFlagExt3;

    @JsonProperty("isConfGroup")
    private int isConfGroup;

    @JsonProperty("isModifyConfGroupFace")
    private int isModifyConfGroupFace;

    @JsonProperty("isModifyConfGroupName")
    private int isModifyConfGroupName;

    @JsonProperty("groupFlagExt4")
    private long groupFlagExt4;

    @JsonProperty("groupMemo")
    private String groupMemo;

    @JsonProperty("cmdUinMsgSeq")
    private long cmdUinMsgSeq;

    @JsonProperty("cmdUinJoinTime")
    private long cmdUinJoinTime;

    @JsonProperty("cmdUinUinFlag")
    private int cmdUinUinFlag;

    @JsonProperty("cmdUinMsgMask")
    private int cmdUinMsgMask;

    @JsonProperty("groupSecLevelInfo")
    private int groupSecLevelInfo;

    @JsonProperty("cmdUinPrivilege")
    private int cmdUinPrivilege;

    @JsonProperty("cmdUinFlagEx2")
    private int cmdUinFlagEx2;

    @JsonProperty("appealDeadline")
    private long appealDeadline;

    @JsonProperty("remarkName")
    private String remarkName;

    @JsonProperty("isTop")
    private boolean isTop;

    @JsonProperty("groupFace")
    private int groupFace;

    @JsonProperty("groupGeoInfo")
    private GroupGeoInfo groupGeoInfo;

    @JsonProperty("certificationText")
    private String certificationText;

    @JsonProperty("cmdUinRingtoneId")
    private int cmdUinRingtoneId;

    @JsonProperty("longGroupName")
    private String longGroupName;

    @JsonProperty("autoAgreeJoinGroupUserNumForConfGroup")
    private int autoAgreeJoinGroupUserNumForConfGroup;

    @JsonProperty("autoAgreeJoinGroupUserNumForNormalGroup")
    private int autoAgreeJoinGroupUserNumForNormalGroup;

    @JsonProperty("cmdUinFlagExt3Grocery")
    private int cmdUinFlagExt3Grocery;

    @JsonProperty("groupCardPrefix")
    private GroupCardPrefix groupCardPrefix;

    @JsonProperty("groupExt")
    private GroupExt groupExt;

    @JsonProperty("msgLimitFrequency")
    private int msgLimitFrequency;

    @JsonProperty("hlGuildAppid")
    private int hlGuildAppid;

    @JsonProperty("hlGuildSubType")
    private int hlGuildSubType;

    @JsonProperty("isAllowRecallMsg")
    private int isAllowRecallMsg;

    @JsonProperty("confUin")
    private String confUin;

    @JsonProperty("confMaxMsgSeq")
    private int confMaxMsgSeq;

    @JsonProperty("confToGroupTime")
    private int confToGroupTime;

    @JsonProperty("groupSchoolInfo")
    private GroupSchoolInfo groupSchoolInfo;

    @JsonProperty("activeMemberNum")
    private int activeMemberNum;

    @JsonProperty("groupGrade")
    private int groupGrade;

    @JsonProperty("groupCreateTime")
    private long groupCreateTime;

    @JsonProperty("subscriptionUin")
    private String subscriptionUin;

    @JsonProperty("subscriptionUid")
    private String subscriptionUid;

    @JsonProperty("noFingerOpenFlag")
    private int noFingerOpenFlag;

    @JsonProperty("noCodeFingerOpenFlag")
    private int noCodeFingerOpenFlag;

    @JsonProperty("isGroupFreeze")
    private int isGroupFreeze;

    @JsonProperty("allianceId")
    private String allianceId;

    @JsonProperty("groupExtOnly")
    private GroupExtOnly groupExtOnly;

    @JsonProperty("isAllowConfGroupMemberModifyGroupName")
    private int isAllowConfGroupMemberModifyGroupName;

    @JsonProperty("isAllowConfGroupMemberNick")
    private int isAllowConfGroupMemberNick;

    @JsonProperty("isAllowConfGroupMemberAtAll")
    private int isAllowConfGroupMemberAtAll;

    @JsonProperty("groupClassText")
    private String groupClassText;

    @JsonProperty("groupFreezeReason")
    private int groupFreezeReason;

    @JsonProperty("headPortraitSeq")
    private int headPortraitSeq;

    @JsonProperty("groupHeadPortrait")
    private GroupHeadPortrait groupHeadPortrait;

    @JsonProperty("cmdUinJoinMsgSeq")
    private long cmdUinJoinMsgSeq;

    @JsonProperty("cmdUinJoinRealMsgSeq")
    private long cmdUinJoinRealMsgSeq;

    @JsonProperty("groupAnswer")
    private String groupAnswer;

    @JsonProperty("groupAdminMaxNum")
    private int groupAdminMaxNum;

    @JsonProperty("inviteNoAuthNumLimit")
    private String inviteNoAuthNumLimit;

    @JsonProperty("hlGuildOrgId")
    private int hlGuildOrgId;

    @JsonProperty("isAllowHlGuildBinary")
    private int isAllowHlGuildBinary;

    @JsonProperty("localExitGroupReason")
    private int localExitGroupReason;

    @JsonProperty("group_all_shut")
    private int groupAllShut;

    @JsonProperty("group_remark")
    private String groupRemark;

    @JsonProperty("group_id")
    private long groupId;

    @JsonProperty("group_name")
    private String group_name;

    @JsonProperty("member_count")
    private int memberCount;

    @JsonProperty("max_member_count")
    private int maxMemberCount;

    // ---------- 内部类 ----------

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupGeoInfo {
        @JsonProperty("ownerUid")
        private String ownerUid;
        @JsonProperty("SetTime")
        private long setTime;
        @JsonProperty("CityId")
        private int cityId;
        @JsonProperty("Longitude")
        private String longitude;
        @JsonProperty("Latitude")
        private String latitude;
        @JsonProperty("GeoContent")
        private String geoContent;
        @JsonProperty("poiId")
        private String poiId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupCardPrefix {
        @JsonProperty("introduction")
        private String introduction;
        @JsonProperty("rptPrefix")
        private List<String> rptPrefix;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupExt {
        @JsonProperty("groupInfoExtSeq")
        private int groupInfoExtSeq;
        @JsonProperty("reserve")
        private int reserve;
        @JsonProperty("luckyWordId")
        private String luckyWordId;
        @JsonProperty("lightCharNum")
        private int lightCharNum;
        @JsonProperty("luckyWord")
        private String luckyWord;
        @JsonProperty("starId")
        private int starId;
        @JsonProperty("essentialMsgSwitch")
        private int essentialMsgSwitch;
        @JsonProperty("todoSeq")
        private int todoSeq;
        @JsonProperty("blacklistExpireTime")
        private long blacklistExpireTime;
        @JsonProperty("isLimitGroupRtc")
        private int isLimitGroupRtc;
        @JsonProperty("companyId")
        private int companyId;
        @JsonProperty("hasGroupCustomPortrait")
        private int hasGroupCustomPortrait;
        @JsonProperty("bindGuildId")
        private String bindGuildId;

        @JsonProperty("groupOwnerId")
        private GroupOwnerId groupOwnerId;

        @JsonProperty("essentialMsgPrivilege")
        private int essentialMsgPrivilege;
        @JsonProperty("msgEventSeq")
        private String msgEventSeq;
        @JsonProperty("inviteRobotSwitch")
        private int inviteRobotSwitch;
        @JsonProperty("gangUpId")
        private String gangUpId;
        @JsonProperty("qqMusicMedalSwitch")
        private int qqMusicMedalSwitch;
        @JsonProperty("showPlayTogetherSwitch")
        private int showPlayTogetherSwitch;
        @JsonProperty("groupFlagPro1")
        private String groupFlagPro1;

        @JsonProperty("groupBindGuildIds")
        private GuildIds groupBindGuildIds;

        @JsonProperty("viewedMsgDisappearTime")
        private String viewedMsgDisappearTime;

        @JsonProperty("groupExtFlameData")
        private GroupExtFlameData groupExtFlameData;

        @JsonProperty("groupBindGuildSwitch")
        private int groupBindGuildSwitch;
        @JsonProperty("groupAioBindGuildId")
        private String groupAioBindGuildId;

        @JsonProperty("groupExcludeGuildIds")
        private GuildIds groupExcludeGuildIds;

        @JsonProperty("fullGroupExpansionSwitch")
        private int fullGroupExpansionSwitch;
        @JsonProperty("fullGroupExpansionSeq")
        private String fullGroupExpansionSeq;
        @JsonProperty("inviteRobotMemberSwitch")
        private int inviteRobotMemberSwitch;
        @JsonProperty("inviteRobotMemberExamine")
        private int inviteRobotMemberExamine;
        @JsonProperty("groupSquareSwitch")
        private int groupSquareSwitch;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupOwnerId {
        @JsonProperty("memberUin")
        private String memberUin;
        @JsonProperty("memberUid")
        private String memberUid;
        @JsonProperty("memberQid")
        private String memberQid;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuildIds {
        @JsonProperty("guildIds")
        private List<String> guildIds;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupExtFlameData {
        @JsonProperty("switchState")
        private int switchState;
        @JsonProperty("state")
        private int state;
        @JsonProperty("dayNums")
        private List<Integer> dayNums;
        @JsonProperty("version")
        private int version;
        @JsonProperty("updateTime")
        private String updateTime;
        @JsonProperty("isDisplayDayNum")
        private boolean isDisplayDayNum;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupSchoolInfo {
        @JsonProperty("location")
        private String location;
        @JsonProperty("grade")
        private int grade;
        @JsonProperty("school")
        private String school;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupExtOnly {
        @JsonProperty("tribeId")
        private int tribeId;
        @JsonProperty("moneyForAddGroup")
        private int moneyForAddGroup;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupHeadPortrait {
        @JsonProperty("portraitCnt")
        private int portraitCnt;
        @JsonProperty("portraitInfo")
        private List<String> portraitInfo;
        @JsonProperty("defaultId")
        private int defaultId;
        @JsonProperty("verifyingPortraitCnt")
        private int verifyingPortraitCnt;
        @JsonProperty("verifyingPortraitInfo")
        private List<String> verifyingPortraitInfo;
    }
}
