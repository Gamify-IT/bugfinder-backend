package de.unistuttgart.bugfinder.gameresult;

import de.unistuttgart.bugfinder.solution.SolutionDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Data Transfer Object which contains for a code the specific solution submitted by a player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmittedSolutionDTO {
    UUID codeId;
    SolutionDTO solution;
}
