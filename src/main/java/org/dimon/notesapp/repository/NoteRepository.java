package org.dimon.notesapp.repository;

import org.dimon.notesapp.model.Note;
import org.dimon.notesapp.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
    Page<Note> findByTags(Tag tag, Pageable pageable);
}
