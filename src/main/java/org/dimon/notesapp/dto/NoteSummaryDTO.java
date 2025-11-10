package org.dimon.notesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class NoteSummaryDTO {
    private String id;
    private String title;
    private Instant createdDate;
}
