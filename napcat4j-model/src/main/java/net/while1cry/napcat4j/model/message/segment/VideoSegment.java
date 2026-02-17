package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class VideoSegment extends Segment {

    @NotNull
    private final String file;
    @Nullable
    private final String name;
    @Nullable
    private final String thumb;
    @Nullable
    private final String url;
    @Nullable
    private final String path;
    @Nullable
    private final String fileId;
    @Nullable
    private final String fileSize;
    @Nullable
    private final String fileUnique;

    public VideoSegment(
            @NotNull String file,
            @Nullable String name,
            @Nullable String thumb,
            @Nullable String url,
            @Nullable String path,
            @Nullable String fileId,
            @Nullable String fileSize,
            @Nullable String fileUnique
    ) {
        super("video");
        this.file = file;
        this.name = name;
        this.thumb = thumb;
        this.url = url;
        this.path = path;
        this.fileId = fileId;
        this.fileSize = fileSize;
        this.fileUnique = fileUnique;
    }

    @Override
    public Map<String, String> getData() {
        return Collections.unmodifiableMap(new HashMap<>() {{
            put("file", file);
            put("name", name);
            put("thumb", thumb);
            put("url", url);
            put("path", path);
            put("file_id", fileId);
            put("file_size", fileSize);
            put("file_unique", fileUnique);
        }});
    }
}
