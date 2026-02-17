package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class ImageSegment extends Segment {

    @Nullable
    private final String name;
    @Nullable
    private final String summary;
    @Nullable
    private final String subType;
    @Nullable
    private final String url;
    @Nullable
    private final String path;
    @NotNull
    private final String file;
    @Nullable
    private final String fileId;
    @Nullable
    private final String fileSize;
    @Nullable
    private final String fileUnique;

    public ImageSegment(
            @Nullable String name,
            @Nullable String summary,
            @Nullable String subType,
            @Nullable String url,
            @Nullable String path,
            @NotNull String file,
            @Nullable String fileId,
            @Nullable String fileSize,
            @Nullable String fileUnique
    ) {
        super("image");
        this.name = name;
        this.summary = summary;
        this.subType = subType;
        this.url = url;
        this.path = path;
        this.file = file;
        this.fileId = fileId;
        this.fileSize = fileSize;
        this.fileUnique = fileUnique;
    }

    @Override
    public Map<String, String> getData() {
        return Collections.unmodifiableMap(new HashMap<>() {{
            put("name", name);
            put("summary", summary);
            put("sub_type", subType);
            put("url", url);
            put("path", path);
            put("file", file);
            put("file_id", fileId);
            put("file_size", fileSize);
            put("file_unique", fileUnique);
        }});
    }
}
