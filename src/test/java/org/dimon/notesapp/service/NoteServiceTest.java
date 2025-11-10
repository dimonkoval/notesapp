package org.dimon.notesapp.service;

import org.dimon.notesapp.dto.NoteDetailDTO;
import org.dimon.notesapp.dto.NoteSummaryDTO;
import org.dimon.notesapp.model.Note;
import org.dimon.notesapp.model.Tag;
import org.dimon.notesapp.repository.NoteRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock NoteRepository repo;
    @InjectMocks NoteService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNote_shouldReturnSavedNote() {
        Note n = new Note("title", "text", Set.of(Tag.PERSONAL));
        when(repo.save(any())).thenReturn(n);

        Note result = service.create("title", "text", Set.of(Tag.PERSONAL));

        assertEquals("title", result.getTitle());
        assertEquals("text", result.getText());
        assertTrue(result.getTags().contains(Tag.PERSONAL));
        verify(repo).save(any());
    }

    @Test
    void updateNote_existingNote_shouldUpdateAndReturn() {
        Note n = new Note("old", "old text", Set.of(Tag.BUSINESS));
        n.setId("1");
        when(repo.findById("1")).thenReturn(Optional.of(n));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Note updated = service.update("1", "new title", "new text", Set.of(Tag.PERSONAL));

        assertEquals("new title", updated.getTitle());
        assertEquals("new text", updated.getText());
        assertTrue(updated.getTags().contains(Tag.PERSONAL));
        verify(repo).save(any());
    }

    @Test
    void updateNote_nonExistingNote_shouldThrow() {
        when(repo.findById("1")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.update("1", "t", "txt", Set.of()));
    }

    @Test
    void deleteNote_shouldCallRepoDelete() {
        service.delete("1");
        verify(repo).deleteById("1");
    }

    @Test
    void listNotes_withoutTag_shouldReturnPageOfDTOs() {
        Note n1 = new Note("t1", "txt1", Set.of());
        n1.setCreatedDate(Instant.now());
        Page<Note> page = new PageImpl<>(List.of(n1));
        when(repo.findAll(any(Pageable.class))).thenReturn(page);

        Page<NoteSummaryDTO> result = service.list(Optional.empty(), 0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("t1", result.getContent().get(0).getTitle());
    }

    @Test
    void getDetail_existingNote_shouldReturnDTO() {
        Note n = new Note("t", "txt", Set.of(Tag.PERSONAL));
        n.setId("1");
        n.setCreatedDate(Instant.now());
        when(repo.findById("1")).thenReturn(Optional.of(n));

        NoteDetailDTO dto = service.getDetail("1");

        assertEquals("1", dto.getId());
        assertEquals("t", dto.getTitle());
        assertEquals("txt", dto.getText());
        assertTrue(dto.getTags().contains(Tag.PERSONAL));
    }

    @Test
    void stats_shouldReturnWordCountsSorted() {
        Note n = new Note("t", "word test word another", Set.of());
        n.setId("1");
        when(repo.findById("1")).thenReturn(Optional.of(n));

        Map<String, Long> counts = service.stats("1");

        Iterator<Map.Entry<String, Long>> it = counts.entrySet().iterator();
        Map.Entry<String, Long> first = it.next();
        assertEquals("word", first.getKey());  // слово "word" повторюється 2 рази
        assertEquals(2L, first.getValue());
    }
}
