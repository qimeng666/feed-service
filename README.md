1. 用户 发起 POST /notes 请求到 note-service

2. note-service 保存笔记后，发布 note-creation-event 到 Kafka Topic

3. feed-service 作为Kafka消费者订阅并消费该事件

4. feed-service 调用 user-service 内部接口 /internal/users/{id}/followers 获取粉丝列表

5. feed-service 将新的笔记 ID 写入到数据库表 user_feed


