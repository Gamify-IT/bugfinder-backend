package de.unistuttgart.bugfinder.solution.bug;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BugDTO {

    String id;
    String wordId;
    ErrorType errorType;
    String correctValue;
}
