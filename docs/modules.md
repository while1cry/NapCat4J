# NapCat4J 模块化规范

本文件说明 NapCat4J 项目的模块化原则、模块职责、依赖规则及命名规范。
目标是保证框架清晰、可维护，并为贡献者提供明确边界。

---

## 1. 模块列表与职责

| 模块                   | 职责                  | 允许依赖                                   |
|----------------------|---------------------|----------------------------------------|
| `napcat4j-model`     | 数据与对象模型层            | 不依赖任何 NapCat4J 模块                      |
| `napcat4j-core`      | 协议与通信层              | 可以依赖 `model`，不依赖 `cache` 或 `framework` |
| `napcat4j-cache`     | 本地状态缓存              | 依赖 `model`，不依赖 `core` 或 `framework`    |
| `napcat4j-framework` | 高层客户端框架、事件系统、API 封装 | 可以依赖 `core` + `cache`                  |

---

## 2. 模块依赖原则

1. **单向依赖**  
   高层模块可以依赖低层模块，低层模块不能依赖高层模块。  
   避免循环依赖。

2. **核心模块隔离**  
   `core` 不依赖 `framework` 或 `cache`，确保协议层可独立使用。

3. **模型模块独立**  
   `model` 仅包含 POJO / Event / Message / User 等对象，不依赖任何业务逻辑或网络层。

4. **缓存模块可插拔**  
   `cache` 可抽象成 `CacheProvider`，框架层通过接口调用，不直接依赖实现细节。

---

## 3. 模块包命名规范

统一 Java 包名风格：

```text
me.while1cry.napcat4j.model
me.while1cry.napcat4j.core
me.while1cry.napcat4j.cache
me.while1cry.napcat4j.framework
```

* 子包根据功能划分，例如 `me.while1cry.napcat4j.model.message`、`me.while1cry.napcat4j.core.websocket`
* 避免跨模块使用 `internal` 包以外的类

---

## 4. Gradle 多模块配置示例

```kotlin
// settings.gradle.kts
rootProject.name = "napcat4j"
include("napcat4j-core", "napcat4j-model", "napcat4j-cache", "napcat4j-framework")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
```

模块依赖示例：

```kotlin
// napcat4j-framework/build.gradle.kts
dependencies {
    api(projects.napcat4j.core)
    api(projects.napcat4j.cache)
}
```

---

## 5. 模块更新与版本管理

1. 每个模块可以独立发布快照，但核心版本号需统一。
2. 大版本更新时保证：

    * **低层模块接口向后兼容**
    * **高层模块可依赖最新低层模块**
3. 对外 API 变化需在 CHANGELOG 明确记录模块影响范围。

---

## 6. 模块间通信规范

* **事件与消息**：跨模块交互必须通过事件对象或接口，不直接访问内部状态。
* **Cache 访问**：通过接口访问缓存，框架层不要直接修改缓存内部数据结构。
* **协议调用**：框架层通过 core 提供的 API 调用协议，不操作 WebSocket / HTTP 细节。

---

## 7️. 代码示例

```java
// model 模块
package me.while1cry.napcat4j.model.user;

import lombok.Getter;

@Getter
public class User {

  private final long id;
  private final String nickname;
}
```  

```java
// core 模块
package me.while1cry.napcat4j.core.websocket;

import me.while1cry.napcat4j.model.user.User;

public interface WebSocketClient {

  void send(User user, String message);
}
```

> 低层模块依赖 model，高层模块依赖 core + cache。

---

## 8. 最佳实践

* 每个模块独立测试
* 模块内部尽量使用 `internal` 或 package-private 修饰，暴露必要 API
* 保持模块边界清晰，方便未来插件化和扩展