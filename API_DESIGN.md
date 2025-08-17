# Feed 服务 API 设计说明文档

## 概述

Feed 服务是一个微服务模块，负责为用户提供个性化的内容流。该服务通过查询用户关注列表，获取关注用户的最新笔记，并按发布时间倒序排序返回给用户。

## 技术栈

- **框架**: Spring Boot 3.5.3
- **数据库**: MySQL
- **消息队列**: Apache Kafka
- **服务调用**: OpenFeign
- **Java 版本**: 17

## API 端点

### 1. 获取用户 Feed 流

**端点**: `GET /feed/{userId}`

**描述**: 根据用户关注列表获取最新笔记，按发布时间倒序排序

**路径参数**:

- `userId` (Long): 用户 ID

**查询参数**:

- `limit` (Integer, 可选): 返回结果数量限制，默认 20

**请求示例**:

```http
GET /feed/123?limit=10
```

**响应示例**:

```json
[
  {
    "note": {
      "id": 456,
      "authorId": 789,
      "title": "我的第一篇笔记",
      "content": "这是笔记内容...",
      "createdTime": "2024-01-15T10:30:00"
    },
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "note": {
      "id": 455,
      "authorId": 790,
      "title": "技术分享",
      "content": "今天分享一些技术心得...",
      "createdTime": "2024-01-15T09:15:00"
    },
    "createdAt": "2024-01-15T09:15:00"
  }
]
```

### 2. 获取用户 Feed 流（详细版本）

**端点**: `GET /feed/{userId}/detailed`

**描述**: 获取用户 Feed 流，包含更详细的缓存信息

**路径参数**:

- `userId` (Long): 用户 ID

**查询参数**:

- `limit` (Integer, 可选): 返回结果数量限制，默认 20

**请求示例**:

```http
GET /feed/123/detailed?limit=10
```

### 3. 获取用户 Feed 流（通过笔记端点）

**端点**: `GET /feed/notes/{userId}`

**描述**: 根据用户关注列表获取最新笔记，按发布时间倒序排序（与主 Feed 流功能相同）

**路径参数**:

- `userId` (Long): 用户 ID

**请求示例**:

```http
GET /feed/notes/123
```

## 业务流程

### Feed 流获取流程

1. **查询用户关注列表**

   - 调用用户服务获取当前用户关注的所有用户 ID 列表
   - 接口: `GET /internal/users/{userId}/following`

2. **批量查询最新笔记**

   - 根据关注用户 ID 列表，逐个查询这些用户发布的最新笔记
   - 接口: `GET /internal/notes/by-authors?authorIds={authorId}`

3. **聚合排序**
   - 将所有笔记按发布时间倒序排序
   - 限制返回结果数量
   - 封装为 FeedDto 格式返回

## 数据模型

### FeedDto

```java
public class FeedDto {
    private NoteDto note;           // 笔记信息
    private LocalDateTime createdAt; // 创建时间
}
```

### NoteDto

```java
public class NoteDto {
    private Long id;                // 笔记ID
    private Long authorId;          // 作者ID
    private String title;           // 标题
    private String content;         // 内容
    private LocalDateTime createdTime; // 创建时间
}
```

### UserFeed (数据库实体)

```java
public class UserFeed {
    private Long id;                // 主键ID
    private Long userId;            // 用户ID
    private Long noteId;            // 笔记ID
    private LocalDateTime createdTime; // 创建时间
}
```

## 外部服务依赖

### 用户服务 (User Service)

- **URL**: `http://localhost:8081` (可通过环境变量 `USER_SERVICE_URL` 配置)
- **接口**:
  - `GET /internal/users/{userId}/following` - 获取用户关注列表

### 笔记服务 (Note Service)

- **URL**: `http://localhost:8080` (可通过环境变量 `NOTE_SERVICE_URL` 配置)
- **接口**:
  - `GET /internal/notes/by-authors?authorIds={authorId}` - 根据单个作者 ID 获取笔记

## 配置说明

### 应用配置 (application.yml)

```yaml
server:
  port: 8082

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/feed_service_db
    username: root
    password: 1057555034

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: feed-service-group
```

### 环境变量

- `USER_SERVICE_URL`: 用户服务地址
- `NOTE_SERVICE_URL`: 笔记服务地址
- `KAFKA_BOOTSTRAP_SERVERS`: Kafka 服务器地址

## 错误处理

### HTTP 状态码

- `200 OK`: 请求成功
- `400 Bad Request`: 请求参数错误
- `404 Not Found`: 用户不存在
- `500 Internal Server Error`: 服务器内部错误

### 错误响应格式

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid user ID",
  "path": "/feed/123"
}
```

## 性能考虑

1. **批量查询**: 使用批量接口减少网络调用次数
2. **分页限制**: 默认限制返回 20 条记录，避免大量数据传输
3. **缓存机制**: 支持从数据库缓存中获取 Feed 信息
4. **异步处理**: 使用 Kafka 进行异步事件处理

## 扩展性

1. **水平扩展**: 服务无状态，支持多实例部署
2. **数据库分片**: 可根据用户 ID 进行数据分片
3. **缓存层**: 可集成 Redis 等缓存系统
4. **监控**: 支持添加监控和日志记录

## 部署说明

1. 确保 MySQL 数据库已启动并创建数据库 `feed_service_db`
2. 确保用户服务和笔记服务已启动并可访问
3. 启动 Kafka 服务（如果使用事件驱动功能）
4. 运行 Feed 服务应用

```bash
mvn spring-boot:run
```

## 测试

### 单元测试

```bash
mvn test
```

### 集成测试

使用 Postman 或 curl 测试 API 端点：

```bash
# 获取用户Feed流
curl -X GET "http://localhost:8082/feed/123?limit=10"

# 获取详细Feed流
curl -X GET "http://localhost:8082/feed/123/detailed?limit=10"

# 获取用户笔记
curl -X GET "http://localhost:8082/feed/notes/123"
```
