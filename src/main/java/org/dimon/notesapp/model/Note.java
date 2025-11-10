package org.dimon.notesapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.Set;

@Document(collection = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    private String id;
    private String title;
    private String text;
    private Instant createdDate;
    private Set<Tag> tags;

    public Note(String title, String text, Set<Tag> tags) {
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.createdDate = Instant.now();
    }
}
