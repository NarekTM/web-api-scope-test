package com.thevirtugroup.postitnote.service.converter;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.model.Note;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NoteConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss VV");

    public NoteDto toDto(Note note) {
        NoteDto dto = new NoteDto();

        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreationDateTime(toFormattedDateTime(note.getCreationDateTime()));

        return dto;
    }

    public Note toEntity(NoteDto dto) {
        Note note = new Note();

        note.setId(dto.getId());
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());

        return note;
    }

    public Note updateEntityFields(Note noteToBeUpdated, NoteDto noteDto) {
        noteToBeUpdated.setTitle(noteDto.getTitle());
        noteToBeUpdated.setContent(noteDto.getContent());

        return noteToBeUpdated;
    }

    private String toFormattedDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(FORMATTER);
    }
}
