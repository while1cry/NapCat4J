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
public class SendMarketFaceData extends MessageData {

    @Nullable
    public String name;

    @Nullable
    public String summary;

    @NotNull
    public String file = "marketface";
}
