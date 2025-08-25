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
public class GroupOverviewDto {

    @JsonProperty("group_all_shut")
    private int groupAllShut;

    @JsonProperty("group_remark")
    private String groupRemark;

    @JsonProperty("group_id")
    private long groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("member_count")
    private int memberCount;

    @JsonProperty("max_member_count")
    private int maxMemberCount;
}
