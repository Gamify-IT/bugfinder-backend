package de.unistuttgart.bugfinder.gameresult;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The class to specify all details the overworld backend needs to
 * submit a game result of a player.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OverworldResultDTO {

  final String game = "BUGFINDER"; //NOSONAR
  UUID configurationId;

  /**
   * The score achieved in the game.
   */
  long score;

  String userId;

  /**
   * The reward-coins that the player achieved in the current round.
   */
  int rewards;
}
