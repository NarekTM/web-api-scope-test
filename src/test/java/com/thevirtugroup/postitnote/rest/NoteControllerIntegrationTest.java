package com.thevirtugroup.postitnote.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.Application;
import com.thevirtugroup.postitnote.config.SpyBeanConfig;
import com.thevirtugroup.postitnote.dto.NoteDto;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NoteRepository;
import com.thevirtugroup.postitnote.service.NoteService;
import com.thevirtugroup.postitnote.service.converter.NoteConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, SpyBeanConfig.class})
@WebAppConfiguration
public class NoteControllerIntegrationTest {

    private static final String BASE_URL = "/api/notes";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoteService spiedNoteService;

    @Autowired
    private NoteRepository spiedNoteRepository;

    @Autowired
    private NoteConverter spiedNoteConverter;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        NoteDto firstNote = getNoteDto("First Note", "This is the content of the first note.");
        NoteDto secondNote = getNoteDto("Second Note", "This is the content of the second note.");

        spiedNoteService.createNote(firstNote);
        spiedNoteService.createNote(secondNote);
    }

    @Before
    public void resetSpies() {
        Mockito.reset(spiedNoteService, spiedNoteRepository, spiedNoteConverter);
    }

    private static NoteDto getNoteDto(String title, String content) {
        NoteDto dto = new NoteDto();

        dto.setTitle(title);
        dto.setContent(content);

        return dto;
    }

    @Test
    public void should_ReturnAllNotes() throws Exception {
        String responseBody = mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<NoteDto> returnedNoteDtos = objectMapper.readValue(responseBody, new TypeReference<List<NoteDto>>() {
        });

        assertThat(returnedNoteDtos).hasSize(2);

        NoteDto firstReturnedNoteDto = returnedNoteDtos.get(0);
        assertThat(firstReturnedNoteDto.getId()).isNotNull();
        assertThat(firstReturnedNoteDto.getTitle()).isEqualTo("First Note");

        NoteDto secondReturnedNoteDto = returnedNoteDtos.get(1);
        assertThat(secondReturnedNoteDto.getId()).isNotNull();
        assertThat(secondReturnedNoteDto.getTitle()).isEqualTo("Second Note");

        Mockito.verify(spiedNoteService).getAllNotes();
        Mockito.verify(spiedNoteRepository).getAll();
        Mockito.verify(spiedNoteConverter, times(2)).toDto(any(Note.class));
    }

    @Test
    public void should_CreateANote() throws Exception {
        NoteDto noteDto = getNoteDto("Test Title", "Test Content");

        String responseBody = mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(noteDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        NoteDto returnedNoteDto = objectMapper.readValue(responseBody, NoteDto.class);

        assertThat(returnedNoteDto.getId()).isNotNull();
        assertThat(returnedNoteDto.getTitle()).isEqualTo("Test Title");
        assertThat(returnedNoteDto.getContent()).isEqualTo("Test Content");

        Mockito.verify(spiedNoteService).createNote(any(NoteDto.class));
        Mockito.verify(spiedNoteRepository).save(any(Note.class));
        Mockito.verify(spiedNoteConverter).toEntity(any(NoteDto.class));
        Mockito.verify(spiedNoteConverter).toDto(any(Note.class));
    }

    @After
    public void tearDown() {
        spiedNoteRepository.deleteAll();
    }
}
