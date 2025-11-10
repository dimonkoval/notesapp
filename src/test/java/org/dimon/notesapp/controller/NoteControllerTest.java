package org.dimon.notesapp.controller;

import org.dimon.notesapp.dto.CreateNoteRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Test
    void createAndGetNote() throws Exception {
        CreateNoteRequest req = new CreateNoteRequest();
        req.setTitle("Hello");
        req.setText("note is just a note");
        mvc.perform(post("/api/notes")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/notes?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void createFailsWithoutTitle() throws Exception {
        CreateNoteRequest req = new CreateNoteRequest();
        req.setTitle("");
        req.setText("text");
        mvc.perform(post("/api/notes")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void statsCountsUniqueWords() throws Exception {
        CreateNoteRequest req = new CreateNoteRequest();
        req.setTitle("t");
        req.setText("note is just a note");
        String resp = mvc.perform(post("/api/notes")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        String id = resp.substring(resp.lastIndexOf('/') + 1);
        mvc.perform(get("/api/notes/" + id + "/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value(2))
                .andExpect(jsonPath("$.is").value(1));
    }
}