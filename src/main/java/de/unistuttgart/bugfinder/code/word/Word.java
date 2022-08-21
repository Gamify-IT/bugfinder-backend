package de.unistuttgart.bugfinder.code.word;

import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Word {

  @Id
  @GeneratedValue(generator = "uuid")
  UUID id;

  String word;

  public Word(final String word) {
    this.word = word;
  }
}
