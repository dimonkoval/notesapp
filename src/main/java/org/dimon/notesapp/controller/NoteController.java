package org.dimon.notesapp.controller;

import org.dimon.notesapp.dto.CreateNoteRequest;
import org.dimon.notesapp.dto.NoteDetailDTO;
import org.dimon.notesapp.dto.NoteSummaryDTO;
import org.dimon.notesapp.model.Note;
import org.dimon.notesapp.model.Tag;
import org.dimon.notesapp.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@Validated
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateNoteRequest req) {
        Note created = service.create(req.getTitle(), req.getText(), req.getTags());
        return ResponseEntity.created(URI.create("/api/notes/" + created.getId())).build();
    }

    @GetMapping
    public Page<NoteSummaryDTO> list(
            @RequestParam Optional<Tag> tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.list(tag, page, size);
    }

    @GetMapping("/{id}")
    public NoteDetailDTO get(@PathVariable String id) {
        return service.getDetail(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody CreateNoteRequest req) {
        service.update(id, req.getTitle(), req.getText(), req.getTags());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stats")
    public Map<String, Long> stats(@PathVariable String id) {
        return service.stats(id);
    }
}

