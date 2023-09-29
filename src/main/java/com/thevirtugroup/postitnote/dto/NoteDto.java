package com.thevirtugroup.postitnote.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class NoteDto {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String creationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }


    @Override
    public String toString() {
        return "NoteDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDateTime='" + creationDateTime + '\'' +
                '}';
    }
}
