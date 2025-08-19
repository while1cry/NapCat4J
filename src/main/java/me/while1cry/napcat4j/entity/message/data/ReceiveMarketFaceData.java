package me.while1cry.napcat4j.entity.message.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMarketFaceData extends MessageData {

    @Nullable
    public String summary;
    @NotNull
    public String file = "marketface";
    @JsonProperty("file_id")
    @NotNull
    public String fileId;
    @NotNull
    public String url;
    @NotNull
    public String path;
    @NotNull
    @JsonProperty("file_unique")
    public String fileUnique;
}
