package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.Word;
import de.unistuttgart.bugfinder.code.word.WordDTO;
import java.util.List;
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
public class CodeDTO {

  UUID id;
  List<WordDTO> words;
}
