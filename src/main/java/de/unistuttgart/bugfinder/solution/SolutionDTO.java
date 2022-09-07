package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolutionDTO {

  String id;
  List<BugDTO> bugs;
  CodeDTO code;
}
