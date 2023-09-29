package com.thevirtugroup.postitnote.rest;

import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<NoteDto> getAllNotes() {
        return noteService.getAllNotes();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public NoteDto getNote(@PathVariable Long id) {
        return noteService.getNote(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public NoteDto createNote(@RequestBody @Validated NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id, @RequestBody @Validated NoteDto noteDto) {
        return noteService.updateNote(id, noteDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<NoteDto> deleteNote(@PathVariable Long id) {
        return noteService.deleteNote(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllNotes() {
        noteService.deleteAllNotes();

        return ResponseEntity.ok("All notes were deleted.");
    }
}
