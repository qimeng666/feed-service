package com.example.feed_service.Dto;

import java.time.LocalDateTime;

public class NoteDto {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdTime;

    public NoteDto() {
    }
    public NoteDto(Long id, String title, String content, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return this.id;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof NoteDto)) return false;
        final NoteDto other = (NoteDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$authorId = this.getAuthorId();
        final Object other$authorId = other.getAuthorId();
        if (this$authorId == null ? other$authorId != null : !this$authorId.equals(other$authorId)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$createdTime = this.getCreatedTime();
        final Object other$createdTime = other.getCreatedTime();
        if (this$createdTime == null ? other$createdTime != null : !this$createdTime.equals(other$createdTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NoteDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $authorId = this.getAuthorId();
        result = result * PRIME + ($authorId == null ? 43 : $authorId.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $createdTime = this.getCreatedTime();
        result = result * PRIME + ($createdTime == null ? 43 : $createdTime.hashCode());
        return result;
    }

    public String toString() {
        return "NoteDto(id=" + this.getId() + ", authorId=" + this.getAuthorId() + ", title=" + this.getTitle() + ", content=" + this.getContent() + ", createdTime=" + this.getCreatedTime() + ")";
    }
}
