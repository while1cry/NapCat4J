# NapCat4J 文档总览

欢迎阅读 **NapCat4J 开发与贡献文档**。
本目录包含框架架构、模块化设计、代码规范、日志、校验、异常处理等核心规范。

> 这些文档旨在帮助开发者和贡献者理解框架设计原则，保持代码一致性和框架健壮性。

---

## 推荐阅读顺序

为了快速理解 NapCat4J 框架，我们建议按照以下顺序阅读文档：

1. **[architecture.md](architecture.md)** — 框架整体架构、数据流、事件流
2. **[modules.md](modules.md)** — 模块职责与依赖规则
3. **[coding-style.md](coding-style.md)** — 代码风格、命名规范、注释要求
4. **[validation.md](validation.md)** — 参数校验规范
5. **[error-handling.md](error-handling.md)** — 异常处理与错误策略
6. **[logging.md](logging.md)** — 日志使用规范
7. **[event-handling.md](event-handling.md)** — 事件系统使用规范
8. **[caching.md](caching.md)** — 缓存策略与接口规范
9. **[testing.md](testing.md)** — 单元测试 & 集成测试规范
10. **[contribution.md](contribution.md)** — 贡献指南、PR 流程、提交规范

> 建议先阅读架构与模块化规范，再依次了解编码规范、校验、异常和日志，这样能够形成完整的开发认知。

---

## 使用指南

* 每个模块开发者应先阅读 **architecture.md** 和 **modules.md**
* 代码提交前，必须遵守 **coding-style.md**、**validation.md**、**error-handling.md**、**logging.md**
* 测试和缓存操作请参照 **testing.md** 与 **caching.md**
* 所有贡献者请阅读 **contribution.md** 确保 PR 流程规范

---

## 更新策略

* 文档随版本迭代更新
* 建议在每次大版本或模块接口更新时同步修改文档
* 文档内的示例和规则以 **最新 master 分支** 为准