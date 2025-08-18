package me.while1cry.napcat4j.entity.message.data;

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
    @NotNull
    public String file_id;
    @NotNull
    public String url;
    @NotNull
    public String path;
    @NotNull
    public String file_unique;
}
