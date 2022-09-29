package de.unistuttgart.bugfinder.configuration.vm;

import de.unistuttgart.bugfinder.solution.bug.ErrorType;
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
public class WordVM {

  String correctValue;

  @Nullable
  String displayValue;

  @Nullable
  ErrorType errorType;
}
