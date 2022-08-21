package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.solution.bug.BugDTO;
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
public class SolutionDTO {

    String id;
    List<BugDTO> bugs;
}
