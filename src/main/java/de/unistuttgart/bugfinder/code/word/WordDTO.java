package de.unistuttgart.bugfinder.code.word;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

/**
 * Data Transfer Object for Word.
 *
 * @see Word
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WordDTO {

  @Nullable
  UUID id;

  String word;

  public WordDTO(final String word) {
    this.word = word;
  }
}
