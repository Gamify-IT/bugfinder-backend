package de.unistuttgart.bugfinder.gameresult;

import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Data Transfer Object to send game results of a player's game run of a configuration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameResultDTO {

  UUID configurationId;
  /**
   * Each submitted solution contains the solution of one code.
   */
  List<SubmittedSolutionDTO> submittedSolutions;

  long score;
  int rewards;

}
