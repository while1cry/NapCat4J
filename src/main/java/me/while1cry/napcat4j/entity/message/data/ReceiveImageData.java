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
public class ReceiveImageData extends MessageData {

    @Nullable
    public String summary;
    @NotNull
    public String file;
    @Nullable
    public String sub_type;
    @NotNull
    public String file_id;
    @NotNull
    public String url;
    @NotNull
    public String path;
    @NotNull
    public String file_size;
    @NotNull
    public String file_unique;
}
