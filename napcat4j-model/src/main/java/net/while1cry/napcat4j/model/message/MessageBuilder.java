package net.while1cry.napcat4j.model.message;

import java.util.ArrayList;
import java.util.List;
import net.while1cry.napcat4j.model.message.segment.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageBuilder {

  private final List<Segment> segments;

  public MessageBuilder() {
    this.segments = new ArrayList<>();
  }

  public MessageBuilder text(String text) {
    segments.add(new TextSegment(text));
    return this;
  }

  public MessageBuilder nextLine() {
    segments.add(new TextSegment("\n"));
    return this;
  }

  public MessageBuilder face(String id) {
    segments.add(new FaceSegment(id));
    return this;
  }

  public MessageBuilder image(
      @Nullable String name,
      @Nullable String summary,
      @Nullable String subType,
      @Nullable String url,
      @Nullable String path,
      @NotNull String file,
      @Nullable String fileId,
      @Nullable String fileSize,
      @Nullable String fileUnique) {
    segments.add(
        new ImageSegment(name, summary, subType, url, path, file, fileId, fileSize, fileUnique));
    return this;
  }

  public MessageBuilder record(
      @NotNull String file,
      @Nullable String name,
      @Nullable String url,
      @Nullable String path,
      @Nullable String fileId,
      @Nullable String fileSize,
      @Nullable String fileUnique) {
    segments.add(new RecordSegment(file, name, url, path, fileId, fileSize, fileUnique));
    return this;
  }

  public MessageBuilder video(
      @NotNull String file,
      @Nullable String name,
      @Nullable String thumb,
      @Nullable String url,
      @Nullable String path,
      @Nullable String fileId,
      @Nullable String fileSize,
      @Nullable String fileUnique) {
    segments.add(new VideoSegment(file, name, thumb, url, path, fileId, fileSize, fileUnique));
    return this;
  }

  public MessageBuilder at(String qq) {
    segments.add(new AtSegment(qq));
    return this;
  }

  public MessageBuilder atAll() {
    segments.add(new AtSegment("all"));
    return this;
  }

  public MessageBuilder rps() {
    segments.add(new RPSSegment(null));
    return this;
  }

  public MessageBuilder dice() {
    segments.add(new DiceSegment(null));
    return this;
  }

  public MessageBuilder reply(String id) {
    segments.add(new ReplySegment(id));
    return this;
  }

  public MessageBuilder json(String json) {
    segments.add(new JsonSegment(json));
    return this;
  }

  public MessageBuilder file(
      @NotNull String file,
      @Nullable String name,
      @Nullable String url,
      @Nullable String path,
      @Nullable String fileId,
      @Nullable String fileSize,
      @Nullable String fileUnique) {
    segments.add(new FileSegment(file, name, url, path, fileId, fileSize, fileUnique));
    return this;
  }

  public Message build() {
    return new Message(segments.toArray(new Segment[0]));
  }

  public static MessageBuilder builder() {
    return new MessageBuilder();
  }
}
