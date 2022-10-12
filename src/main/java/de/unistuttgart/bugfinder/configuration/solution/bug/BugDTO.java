package de.unistuttgart.bugfinder.configuration.solution.bug;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.persistence.Enumerated;
import java.util.UUID;

/**
 * Data Transfer Object for Bug.
 *
 * @see Bug
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BugDTO {
    @Nullable
    UUID id;

    UUID wordId;

    @Enumerated
    ErrorType errorType;

    String correctValue;
}
