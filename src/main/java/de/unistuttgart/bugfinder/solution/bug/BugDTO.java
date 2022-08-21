package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.WordDTO;
import java.util.UUID;
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

  UUID id;
  WordDTO word;
  ErrorType errorType;
  String correctValue;
}
