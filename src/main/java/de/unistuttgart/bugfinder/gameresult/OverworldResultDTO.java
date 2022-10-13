package de.unistuttgart.bugfinder.gameresult;

import de.unistuttgart.bugfinder.BugfinderServiceApplication;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The class to specify all details the overworld backend needs to submit a game result of a player.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OverworldResultDTO {

  final String game = BugfinderServiceApplication.GAME_NAME;
  UUID configurationId;
  long score;
  String userId;
}
