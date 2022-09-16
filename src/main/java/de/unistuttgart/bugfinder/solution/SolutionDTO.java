package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import java.util.List;
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
public class SolutionDTO {

  @Nullable
  UUID id;

  List<BugDTO> bugs;
  CodeDTO code;
}
