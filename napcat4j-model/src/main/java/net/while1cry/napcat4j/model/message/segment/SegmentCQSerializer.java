package net.while1cry.napcat4j.model.message.segment;

import net.while1cry.napcat4j.model.message.MessageUtil;

public class SegmentCQSerializer {

  public String serialize(Segment segment) {
    if (segment.getType().equals("text")) {
      return MessageUtil.escape(segment.getData().get("text"), false);
    } else {
      StringBuilder sb = new StringBuilder().append("[CQ:").append(segment.getType());
      segment
          .getData()
          .forEach(
              (key, value) -> {
                if (value != null) {
                  sb.append(",").append(key).append("=").append(MessageUtil.escape(value, true));
                }
              });
      return sb.append("]").toString();
    }
  }
}
