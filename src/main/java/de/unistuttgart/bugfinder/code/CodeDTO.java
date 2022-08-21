package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.WordDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeDTO {

    String id;
    List<WordDTO> words;
}
