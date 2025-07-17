package com.example.feed_service.Dto;

import java.time.LocalDateTime;

public class FeedDto {
    private NoteDto note;
    private LocalDateTime createdAt;

    public FeedDto(NoteDto note, LocalDateTime createdAt) {
        this.note = note;
        this.createdAt = createdAt;
    }

    public NoteDto getNote() {
        return this.note;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setNote(NoteDto note) {
        this.note = note;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FeedDto)) return false;
        final FeedDto other = (FeedDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$note = this.getNote();
        final Object other$note = other.getNote();
        if (this$note == null ? other$note != null : !this$note.equals(other$note)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FeedDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $note = this.getNote();
        result = result * PRIME + ($note == null ? 43 : $note.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "FeedDto(note=" + this.getNote() + ", createdAt=" + this.getCreatedAt() + ")";
    }
}
