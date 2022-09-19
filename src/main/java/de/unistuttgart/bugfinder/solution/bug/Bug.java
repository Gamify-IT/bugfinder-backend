package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.Word;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * A Bug is an error in a word of a code.
 *
 * A list of bugs represent a solution.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bug {

  @Id
  @GeneratedValue(generator = "uuid")
  UUID id;

  @OneToOne
  Word word;

  @Enumerated
  ErrorType errorType;

  String correctValue;

  public Bug(final Word word, final ErrorType errorType, final String correctValue) {
    this.word = word;
    this.errorType = errorType;
    this.correctValue = correctValue;
  }
}
