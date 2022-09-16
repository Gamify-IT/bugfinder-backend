package de.unistuttgart.bugfinder.gameresult;

import de.unistuttgart.bugfinder.solution.SolutionDTO;
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
public class SubmittedSolutionDTO {

  UUID codeId;
  SolutionDTO solution;
}
