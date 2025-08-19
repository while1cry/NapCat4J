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
public class SendImageData extends MessageData {

    @Nullable
    public String name;
    @Nullable
    public String summary;
    @NotNull
    public String file;
    @Nullable
    @JsonProperty("sub_type")
    public String subType;
}
