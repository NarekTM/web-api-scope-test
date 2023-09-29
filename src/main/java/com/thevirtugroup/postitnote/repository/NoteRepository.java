package com.thevirtugroup.postitnote.repository;

import com.thevirtugroup.postitnote.exception.EntityNotFoundException;
import com.thevirtugroup.postitnote.model.Note;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class NoteRepository {

    private final Map<Long, Note> notes = new HashMap<>();
    private final AtomicLong currentIndex = new AtomicLong(0);

    public Collection<Note> getAll() {
        return notes.values();
    }

    public Note getById(Long id) {
        Note note = notes.get(id);
        if (note == null) {
            throw new EntityNotFoundException(String.format("Note with id %d not found.", id));
        }

        return note;
    }

    public Note save(Note note) {
        note.setId(currentIndex.getAndIncrement());
        notes.put(note.getId(), note);
        return note;
    }

    public void update(Long id, Note note) {
        notes.put(id, note);
    }

    public Note delete(Long id) {
        return notes.remove(id);
    }
}
