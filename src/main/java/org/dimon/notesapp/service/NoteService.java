package org.dimon.notesapp.service;

import lombok.RequiredArgsConstructor;
import org.dimon.notesapp.dto.NoteDetailDTO;
import org.dimon.notesapp.dto.NoteSummaryDTO;
import org.dimon.notesapp.model.Note;
import org.dimon.notesapp.model.Tag;
import org.dimon.notesapp.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repo;

    public Note create(String title, String text, Set<Tag> tags) {
        Note note = new Note(title, text, tags);
        return repo.save(note);
    }

    public Note update(String id, String title, String text, Set<Tag> tags) {
        Note n = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Note not found"));
        n.setTitle(title);
        n.setText(text);
        n.setTags(tags);
        return repo.save(n);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public Page<NoteSummaryDTO> list(Optional<Tag> tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Note> p = tag.map(t -> repo.findByTags(t, pageable)).orElseGet(() -> repo.findAll(pageable));
        return p.map(n -> new NoteSummaryDTO(n.getId(), n.getTitle(), n.getCreatedDate()));
    }

    public NoteDetailDTO getDetail(String id) {
        Note n = repo.findById(id).orElseThrow();
        return new NoteDetailDTO(n.getId(), n.getTitle(), n.getText(), n.getCreatedDate(), n.getTags());
    }

    public Map<String, Long> stats(String id) {
        Note n = repo.findById(id).orElseThrow();
        String text = Optional.ofNullable(n.getText()).orElse("");
        // split on non-word, normalize lower case
        Pattern p = Pattern.compile("\\W+");
        String[] tokens = p.split(text.toLowerCase(Locale.ROOT));
        Map<String, Long> counts = Arrays.stream(tokens)
                .filter(s -> !s.isBlank())
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        // sort by descending count and then lexicographically
        return counts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a,b)->a, LinkedHashMap::new));
    }
}

