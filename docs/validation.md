# NapCat4J 参数校验规范

本规范定义框架中对方法参数、事件对象和外部输入的校验要求。
目标是保证：

* 框架内部不出现非法状态
* 提供统一的异常处理方式
* 防止空指针和非法数据传播

---

## 1. 校验原则

1. **防御性编程**

    * 所有公开 API（Framework / Core / Cache）必须对输入参数进行验证
    * 内部模块（Model）可依赖接口调用方保证合法性，但关键入口仍需校验

2. **Fail Fast**

    * 在发现非法参数时立即抛出异常，而不是继续执行

3. **不可破坏内部状态**

    * 任何非法输入都不得破坏缓存或事件系统的稳定性

---

## 2. 参数校验规范

### 2.1 非空校验

* 所有对象参数必须判空
* 可使用 `Objects.requireNonNull` 或自定义工具方法

```java
public void sendMessage(@NotNull User user, @NotNull String message) {
  Objects.requireNonNull(user, "user must not be null");
  Objects.requireNonNull(message, "message must not be null");
}
```

### 2.2 数值范围校验

* ID、数量等数值必须在合理范围内
* 超出范围抛出 `IllegalArgumentException`

```java
if(groupId <=0){
    throw new

IllegalArgumentException("groupId must be positive");
}
```

### 2.3 字符串格式校验

* 对于特定格式字符串（如消息内容、用户名、Token），应验证长度和正则规则

```java
if(nickname.length() >32){
    throw new

IllegalArgumentException("nickname max length is 32");
}
```

### 2.4 集合校验

* 集合参数不得为 null
* 可空集合与非空集合需明确文档说明
* 集合中每个元素可选单独校验

```java
Objects.requireNonNull(userList, "userList must not be null");
if(userList.

isEmpty()){
    throw new

IllegalArgumentException("userList must not be empty");
}
```

---

## 3. 公共校验工具

建议框架提供统一工具类，例如：

```java
package me.while1cry.napcat4j.core.validation;

public final class Validate {

  private Validate() {
  }

  public static void notNull(Object obj, String message) {
    if (obj == null)
      throw new IllegalArgumentException(message);
  }

  public static void positive(long value, String message) {
    if (value <= 0)
      throw new IllegalArgumentException(message);
  }

  public static void notEmpty(String str, String message) {
    if (str == null || str.isEmpty())
      throw new IllegalArgumentException(message);
  }
}
```

> 框架内部和高层 API 调用此工具，保证校验一致性

---

## 4. 异常策略

* **非法参数** → `IllegalArgumentException`
* **空对象** → `NullPointerException`（可选，但推荐用自定义提示信息）
* **业务限制** → 可定义 `ValidationException`，用于更复杂的规则

示例：

```java
if(message.length() >500){
    throw new

ValidationException("message length exceeds 500 characters");
}
```

---

## 5. 注解与自动校验

* 未来可使用 JSR 380 / Bean Validation 注解（如 `@NotNull`, `@Size`）
* 与手动校验结合，自动生成文档和错误信息

```java

@Getter
public class Message {

  @NotNull
  private final String content;
  @NotNull
  private final User sender;
}
```

---

## 6. 校验最佳实践

1. **入口即校验**：高层 API、Listener 回调入口必须校验
2. **统一异常信息**：便于日志记录与调试
3. **不可吞异常**：非法参数必须抛出，不允许 silent fail
4. **可扩展**：公共工具类允许自定义校验规则
5. **可测试**：单元测试覆盖各类边界值

---

## 7. 代码示例

```java
package me.while1cry.napcat4j.framework;

import me.while1cry.napcat4j.core.validation.Validate;
import me.while1cry.napcat4j.model.user.User;

public class MessageService {

  public void sendMessage(User user, String message) {
    Validate.notNull(user, "user must not be null");
    Validate.notEmpty(message, "message must not be empty");
    Validate.positive(user.id(), "userId must be positive");

    // 执行发送逻辑
  }
}
```

---

## 8. CI 与测试

* 单元测试覆盖：

    * null / 空集合 / 越界 / 格式错误等
* CI 可配置：

    * Fail fast 策略，确保非法输入导致构建失败