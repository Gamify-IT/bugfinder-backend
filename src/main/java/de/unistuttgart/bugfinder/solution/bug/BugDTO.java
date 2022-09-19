package de.unistuttgart.bugfinder.solution.bug;

import java.util.UUID;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

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
