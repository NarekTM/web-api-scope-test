package com.thevirtugroup.postitnote.service;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import com.thevirtugroup.postitnote.service.converter.NoteConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    private final NoteRepository noteRepository;
    private final NoteConverter noteConverter;

    @Autowired
    public NoteService(NoteRepository noteRepository, NoteConverter noteConverter) {
        this.noteRepository = noteRepository;
        this.noteConverter = noteConverter;
    }

    public List<NoteDto> getAllNotes() {
        LOGGER.info("Getting all notes from the DB.");
        return noteRepository.getAll().stream()
                .map(noteConverter::toDto)
                .collect(Collectors.toList());
    }

    public NoteDto getNote(Long id) {
        LOGGER.info("Getting a note with id {} from the DB.", id);
        return noteConverter.toDto(noteRepository.getById(id));
    }

    public NoteDto createNote(NoteDto noteDto) {
        Note note = noteConverter.toEntity(noteDto);
        note.setCreationDateTime(ZonedDateTime.now(ZoneId.of("UTC")));
        LOGGER.info("Creating a note with the following details: {}", note);
        Note savedNote = noteRepository.save(note);
        LOGGER.info("Note has been created with id {}", savedNote.getId());

        return noteConverter.toDto(savedNote);
    }

    public ResponseEntity<NoteDto> updateNote(Long id, NoteDto noteDto) {
        Note noteToBeUpdated = noteRepository.getById(id);
        noteRepository.update(id, noteConverter.updateEntityFields(noteToBeUpdated, noteDto));
        LOGGER.info("Updating a note (id {}) with the following details: {}", id, noteDto);

        return ResponseEntity.ok(noteDto);
    }

    public ResponseEntity<NoteDto> deleteNote(Long id) {
        LOGGER.info("Deleting a note with id {}", id);

        return ResponseEntity.ok(
                noteConverter.toDto(
                        noteRepository.delete(id)
                )
        );
    }

    public void deleteAllNotes() {
        LOGGER.info("Deleting all notes.");
        noteRepository.deleteAll();
    }
}
