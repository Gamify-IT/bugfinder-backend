package de.unistuttgart.bugfinder.code.word;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Represents a word in a code.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Word {

  @Id
  @GeneratedValue(generator = "uuid")
  UUID id;

  String wordContent;

  public Word(final String wordContent) {
    this.wordContent = wordContent;
  }
}
