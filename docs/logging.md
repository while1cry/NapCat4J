# NapCat4J 日志规范

本规范指导 NapCat4J 框架及扩展模块如何使用日志系统，保证：

* 可调试性
* 可维护性
* 不泄露敏感信息
* 框架统一日志风格

---

## 1. 日志框架选择

* 使用 **SLF4J API** 作为统一接口
* 框架层不绑定具体实现（如 Logback、Log4j2 等）
* 用户可自行选择日志实现库

**示例依赖：**

```kotlin
api("org.slf4j:slf4j-api:2.0.17")
testImplementation("ch.qos.logback:logback-classic:1.5.29")
```

---

## 2. 日志级别规范

| 级别        | 用途                           |
|-----------|------------------------------|
| **TRACE** | 极细粒度调试信息，一般只在开发或排查问题时打开      |
| **DEBUG** | 调试信息，输出内部状态、请求/响应内容（非敏感）     |
| **INFO**  | 框架正常运行信息，例如登录成功、模块初始化完成      |
| **WARN**  | 潜在问题或异常，但不影响主流程，例如重试、丢弃非关键消息 |
| **ERROR** | 明确错误，需要用户注意，例如网络连接失败、事件处理异常  |

> 框架默认输出 INFO 及以上，开发者可通过日志实现库调整级别。

---

## 3. 日志格式规范

* 输出消息包含 **时间 + 日志级别 + 模块 + 线程 + 消息**
* 示例：

```
2026-02-14 10:00:00 [INFO] [Framework] [Thread-1] Bot login successful: userId=123456
```

* 模块标识建议使用类名或模块名，如 `[Core.WebSocket]`、`[Cache.UserCache]`

---

## 4. 日志内容规范

1. **结构化信息**

    * 尽量输出 key=value 形式，方便解析与分析
    * 示例：

      ```text
      Received message: messageId=987654 userId=123456 groupId=654321 content="Hello World"
      ```

2. **避免敏感信息**

    * 不输出用户密码、Token、验证码、私人信息等
    * 若必要，可部分脱敏：

      ```text
      authToken=******abc
      ```

3. **异常日志**

    * 使用 SLF4J 提供的异常参数：

      ```java
      log.error("WebSocket connection failed for groupId={}", groupId, exception);
      ```
    * 不直接 `printStackTrace()`

---

## 5. 模块日志实践

### 5.1 Core 模块

* 网络连接、协议收发、心跳状态等输出 DEBUG / INFO
* 网络异常输出 WARN / ERROR
* 日志例子：

```java
log.debug("Sending message: {}", message);
log.warn("WebSocket disconnected, will retry in {} ms", retryInterval);
```

### 5.2 Cache 模块

* 缓存加载/更新/失效输出 DEBUG
* 缓存异常输出 WARN
* 不输出全部内部 Map 数据，避免爆日志

```java
log.debug("Cache updated: userId={} key={}", userId, key);
```

### 5.3 Framework 模块

* 事件分发状态输出 INFO / DEBUG
* 未处理异常输出 ERROR
* Listener 异常不中断其他事件，但应记录日志

```java
log.info("Dispatching message event: messageId={}", message.getId());
log.error("Listener failed", exception);
```

---

## 6. 日志最佳实践

1. **统一接口**：所有模块使用 SLF4J API，不直接依赖具体实现
2. **尽量结构化**：key=value，便于日志分析工具抓取
3. **调试与生产区分**：DEBUG / TRACE 只在开发或调试开启
4. **不要打印内部 Map / List 结构**，可能产生大量日志
5. **异常必须记录**：捕获异常后 log.error，必要时 rethrow 或上报
6. **模块标识清晰**：每条日志都能快速定位模块来源

---

## 7. 示例模板

```java
package me.while1cry.napcat4j.core.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketClientImpl {

    private static final Logger log = LoggerFactory.getLogger(WebSocketClientImpl.class);

    public void connect() {
        try {
            log.info("Connecting to NapCat server...");
            // connect logic
        } catch (Exception e) {
            log.error("WebSocket connection failed", e);
        }
    }

    public void sendMessage(long groupId, String message) {
        log.debug("Sending message to groupId={}: {}", groupId, message);
    }
}
```

---

## 8. CI / 规范建议

* 可以在 CI 中验证：

    * ERROR/WARN 日志不要包含敏感信息
    * DEBUG/TRACE 日志可在测试环境开启
* 日志格式尽量固定，可考虑整合 logback.xml / log4j2.xml 模板