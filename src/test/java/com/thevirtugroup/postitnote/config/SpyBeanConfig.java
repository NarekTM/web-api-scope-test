package com.thevirtugroup.postitnote.config;

import com.thevirtugroup.postitnote.repository.NoteRepository;
import com.thevirtugroup.postitnote.service.NoteService;
import com.thevirtugroup.postitnote.service.converter.NoteConverter;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SpyBeanConfig {

    @Bean
    @Primary
    public static NoteService spiedNoteService(NoteService realNoteService) {
        return Mockito.spy(realNoteService);
    }

    @Bean
    @Primary
    public static NoteRepository spiedNoteRepository(NoteRepository realNoteRepository) {
        return Mockito.spy(realNoteRepository);
    }

    @Bean
    @Primary
    public static NoteConverter spiedNoteConverter(NoteConverter realNoteConverter) {
        return Mockito.spy(realNoteConverter);
    }
}
