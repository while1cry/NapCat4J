# NapCat4J

> A High-Level Java Client Framework for NapCatQQ

**NapCat4J** 是一个基于 NapCatQQ 构建的 **高层 Java 客户端框架**。
它不仅封装协议调用，还提供：

* 事件系统
* 对象模型
* 本地状态缓存
* 生命周期管理
* 模块化架构设计

项目定位类似 JDA ——
目标是成为 **Java 生态下 NapCat 的标准客户端实现**。

---

## 项目状态

* 当前版本：`0.4.x`
* 开发阶段：**活跃重构期**
* API 状态：**不稳定（可能发生破坏性变更）**
* 不建议用于生产环境

0.4.x 是架构稳定前的整理阶段，核心目标是：

* 模块边界清晰化
* 缓存机制重构
* 事件模型稳定
* 为 1.0 做准备

---

# 设计目标

NapCat4J 不只是一个“协议 SDK”。

我们希望提供：

* 高层对象模型（User / Group / Message 等）
* 自动状态缓存（可配置）
* 事件驱动架构
* 可扩展框架层
* 清晰的模块分层

目标使用体验：

```java
NapCatClient client = NapCatClient.create(token);

client.onMessage(event -> {
    event.reply("Hello World");
});

client.login();
```

而不是手动处理 JSON 和原始 WebSocket 数据。

---

# 项目结构

当前模块划分如下：

```
napcat4j
├── docs
├── napcat4j-core
├── napcat4j-model
├── napcat4j-cache
└── napcat4j-framework
```

---

## `/docs`

文档目录。

包含：

* 架构设计说明
* 事件流程说明
* 缓存策略设计
* 开发规范

未来将补充完整的开发者文档与示例。

---

## `/napcat4j-core`

**底层通信与协议层。**

职责：

* WebSocket 连接管理
* 鉴权处理
* 心跳机制
* 原始数据收发
* OneBot / NapCat 协议封装
* 底层 API 调用实现

这一层不负责缓存，也不负责高层业务逻辑。

---

## `/napcat4j-model`

**领域对象模型层。**

包含：

* Message
* User
* Group
* Member
* Event
* Action 请求对象

目标是：

> 将原始 JSON 数据映射为强类型 Java 对象。

---

## `/napcat4j-cache`

**状态缓存层。**

负责：

* 群信息缓存
* 用户信息缓存
* 成员列表缓存
* 懒加载更新
* 缓存失效策略

设计目标：

* 减少不必要 API 请求
* 提供近实时状态访问
* 可配置缓存行为

---

## `/napcat4j-framework`

**高层客户端框架层。**

这一层是 NapCat4J 的核心价值所在。

提供：

* 事件分发系统
* Listener 注册机制
* 生命周期管理
* 客户端入口类
* 高层 API 封装
* 扩展能力

如果你只是想使用 NapCat4J ——
只需要依赖 `napcat4j-framework`。

---

# 架构理念

NapCat4J 采用分层架构：

```
Transport (Core)
        ↓
Protocol Mapping
        ↓
Model
        ↓
Cache
        ↓
Framework
```

这种设计允许：

* 独立替换缓存实现
* 独立测试协议层
* 未来扩展 HTTP / Reverse 模式
* 提供更清晰的职责边界

---

# 未来计划

* 支持 HTTP 模式
* 支持 WebSocket Reverse
* 完善缓存更新策略
* 提供更完整的事件体系
* 提供插件扩展能力
* 逐步冻结 API，迈向 1.0

> 暂不接入 Spring。
> 当架构完全稳定后再考虑 Spring Boot Starter。

---

# 贡献指南

欢迎贡献代码、建议和 Issue。

提交 PR 前请注意：

* 保持模块职责清晰
* 不要跨模块直接引用内部实现
* 保持 API 风格一致
* 优先考虑可扩展性

---

# License

本项目基于 MIT License 开源。
你可以自由使用、修改与分发本项目，但请保留原始版权声明。

---

# 致谢 ❤️

* 感谢 NapCatQQ 提供开放协议实现
* 感谢 IntelliJ IDEA 提供优秀的开发工具
* 感谢所有开源贡献者

---

# 结语

NapCat4J 目前仍在构建阶段。

如果你正在寻找一个：

* 面向对象
* 事件驱动
* 可扩展
* 模块清晰

的 NapCat Java 客户端框架 ——

欢迎参与这个项目的成长。