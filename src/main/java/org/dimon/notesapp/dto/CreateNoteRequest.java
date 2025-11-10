package org.dimon.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.dimon.notesapp.model.Tag;
import java.util.Set;

@Getter
@Setter
public class CreateNoteRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private Set<Tag> tags;
}
