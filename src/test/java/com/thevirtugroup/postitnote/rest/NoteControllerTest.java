package com.thevirtugroup.postitnote.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.service.NoteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoteControllerTest {

    private static final String BASE_URL = "/api/notes";

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @InjectMocks
    private ObjectMapper objectMapper;

    @InjectMocks
    private NoteController noteController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }

    @Test
    public void should_ReturnAllNotes() throws Exception {
        NoteDto noteDto = getNoteDto();
        noteDto.setId(1L);

        when(noteService.getAllNotes()).thenReturn(Collections.singletonList(noteDto));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test title"))
                .andExpect(jsonPath("$[0].content").value("Test content"));
    }

    private static NoteDto getNoteDto() {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle("Test title");
        noteDto.setContent("Test content");

        return noteDto;
    }

    @Test
    public void should_CreateANote() throws Exception {
        NoteDto inputNoteDto = getNoteDto();

        NoteDto expectedNoteDto = getNoteDto();
        expectedNoteDto.setId(1L);

        when(noteService.createNote(any(NoteDto.class))).thenReturn(expectedNoteDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputNoteDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(not(isEmptyString())))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }
}
