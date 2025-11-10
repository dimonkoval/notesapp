package org.dimon.notesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.dimon.notesapp.model.Tag;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NoteDetailDTO {
    private String id;
    private String title;
    private String text;
    private Instant createdDate;
    private Set<Tag> tags;
}
