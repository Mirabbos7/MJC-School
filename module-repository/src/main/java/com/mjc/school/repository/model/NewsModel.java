package com.mjc.school.repository.model;

import java.time.LocalDateTime;

public class NewsModel {
    private static long autoId = 1;

    private final Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Long authorId;

    public NewsModel(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId) {
        this.id = autoId++;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content.txt='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", authorId=" + authorId +
                '}';
    }
}
