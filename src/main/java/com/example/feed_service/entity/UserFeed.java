package com.example.feed_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_feed")
public class UserFeed {
    @Id
    @GeneratedValue
    private Long id;          // 数据库自增主键

    private Long userId;
    private Long noteId;
    private LocalDateTime createdTime;

    public UserFeed() {
    }
    public UserFeed(Long id, Long userId, Long noteId, LocalDateTime createdTime) {
        this.id = id;
        this.userId = userId;
        this.noteId = noteId;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getNoteId() {
        return this.noteId;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserFeed)) return false;
        final UserFeed other = (UserFeed) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$noteId = this.getNoteId();
        final Object other$noteId = other.getNoteId();
        if (this$noteId == null ? other$noteId != null : !this$noteId.equals(other$noteId)) return false;
        final Object this$createdTime = this.getCreatedTime();
        final Object other$createdTime = other.getCreatedTime();
        if (this$createdTime == null ? other$createdTime != null : !this$createdTime.equals(other$createdTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserFeed;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $noteId = this.getNoteId();
        result = result * PRIME + ($noteId == null ? 43 : $noteId.hashCode());
        final Object $createdTime = this.getCreatedTime();
        result = result * PRIME + ($createdTime == null ? 43 : $createdTime.hashCode());
        return result;
    }

    public String toString() {
        return "UserFeed(id=" + this.getId() + ", userId=" + this.getUserId() + ", noteId=" + this.getNoteId() + ", createdTime=" + this.getCreatedTime() + ")";
    }
}
