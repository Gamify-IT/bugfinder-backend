package de.unistuttgart.bugfinder.solution.bug;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BugDTO {

  @Nullable
  UUID id;

  UUID wordId;
  ErrorType errorType;
  String correctValue;
}
