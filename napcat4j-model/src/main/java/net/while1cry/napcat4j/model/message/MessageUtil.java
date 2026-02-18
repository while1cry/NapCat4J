package net.while1cry.napcat4j.model.message;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageUtil {

  // CQ码正则表达式：匹配 [CQ:type,key=value,...]
  private static final Pattern CQ_PATTERN =
      Pattern.compile("\\[CQ:([^,\\]]+)((?:,[^,=]+=[^,\\]]+)*)]");

  /**
   * 将字符串分割为数组，每个元素都是一个CQ码或普通文本
   *
   * <p>示例："1234[CQ:face,id=11]567" -> ["1234", "[CQ:face,id=11]", "567"]
   *
   * @param text 原始字符串
   * @return 分割后的字符串数组
   */
  public static String[] splitToArray(String text) {
    if (text == null || text.isEmpty()) {
      return new String[0];
    }

    List<String> result = new ArrayList<>();
    Matcher matcher = CQ_PATTERN.matcher(text);
    int lastEnd = 0;

    while (matcher.find()) {
      if (matcher.start() > lastEnd) {
        String normalText = text.substring(lastEnd, matcher.start());
        if (!normalText.isEmpty()) {
          result.add(normalText);
        }
      }

      result.add(matcher.group());
      lastEnd = matcher.end();
    }

    if (lastEnd < text.length()) {
      String remainingText = text.substring(lastEnd);
      if (!remainingText.isEmpty()) {
        result.add(remainingText);
      }
    }

    return result.toArray(new String[0]);
  }

  /**
   * 2. 获取CQ码类型
   *
   * <p>示例："[CQ:face,id=11]" -> "face"
   *
   * @param cqCode CQ码字符串
   * @return CQ码类型，如果不是有效的CQ码则返回null
   */
  public static String getCQType(String cqCode) {
    if (cqCode == null || cqCode.isEmpty()) {
      return null;
    }

    Matcher matcher = CQ_PATTERN.matcher(cqCode);
    if (matcher.find()) {
      return matcher.group(1); // 第一个捕获组是类型
    }
    return null;
  }

  /**
   * 3. 将CQ码映射为Map
   *
   * <p>示例："[CQ:face,id=11,name=smile]" -> {type=face, id=11, name=smile}
   *
   * @param cqCode CQ码字符串
   * @return 包含所有参数的Map，如果不是有效的CQ码则返回空Map
   */
  public static Map<String, String> parseToMap(String cqCode) {
    Map<String, String> result = new LinkedHashMap<>();

    if (cqCode == null || cqCode.isEmpty()) {
      return result;
    }

    Matcher matcher = CQ_PATTERN.matcher(cqCode);
    if (matcher.find()) {
      // 提取类型
      String type = matcher.group(1);
      result.put("type", type);

      // 提取参数部分
      String params = matcher.group(2);
      if (params != null && !params.isEmpty()) {
        // 解析参数 (格式: ,key=value,key2=value2)
        String[] pairs = params.substring(1).split(","); // 去掉开头的逗号
        for (String pair : pairs) {
          String[] kv = pair.split("=", 2);
          if (kv.length == 2) {
            result.put(kv[0].trim(), kv[1].trim());
          }
        }
      }
    }

    return result;
  }

  /**
   * 对字符串进行CQ码转义，防止特殊字符干扰CQ码解析
   *
   * <p>转义规则：
   *
   * <ul>
   *   <li>{@code &} → {@code &amp;}
   *   <li>{@code [} → {@code &#91;}
   *   <li>{@code ]} → {@code &#93;}
   *   <li>{@code ,} → {@code &#44;}（可选，由参数控制）
   * </ul>
   *
   * @param s 待转义的原始字符串，若为null则返回null
   * @param comma 是否转义逗号 {@code ,}，true表示转义，false表示保留原样
   * @return 转义后的字符串，若输入为null则返回null
   * @see #unescape(String, boolean) 对应的反转义方法
   */
  public static String escape(String s, boolean comma) {
    s = s.replace("&", "&amp;").replace("[", "&#91mp;").replace("]", "&#93;");
    if (comma) s = s.replace(",", "&#44;");
    return s;
  }

  /**
   * 对CQ码转义字符串进行反转义，还原为原始字符串
   *
   * <p>反转义规则（与 {@link #escape(String, boolean)} 对应）：
   *
   * <ul>
   *   <li>{@code &amp;} → {@code &}
   *   <li>{@code &#91;} → {@code [}
   *   <li>{@code &#93;} → {@code ]}
   *   <li>{@code &#44;} → {@code ,}（可选，由参数控制）
   * </ul>
   *
   * @param s 待反转义的转义字符串，若为null则返回null
   * @param comma 是否反转义逗号 {@code &#44;}，true表示反转义，false表示保留原样
   * @return 反转义后的原始字符串，若输入为null则返回null
   * @see #escape(String, boolean) 对应的转义方法
   */
  public static String unescape(String s, boolean comma) {
    s = s.replace("&amp;", "&").replace("&#91mp;", "[").replace("&#93;", "]");
    if (comma) s = s.replace("&#44;", ",");
    return s;
  }
}
