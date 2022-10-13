package de.unistuttgart.bugfinder.gameresult;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for {@code GameResult}.
 * <p>
 * used when the game is finished and the results are sent to the overworld api.
 * </p>
 */
@RestController
@Slf4j
public class GameResultController {

  @Autowired
  private GameResultService gameResultService;

  @Autowired
  private JWTValidatorService jwtValidatorService;

  /**
   * Sends the game results to the overworld api.
   *
   * @param accessToken   the access token of the user stored in the users cookies
   * @param gameResultDTO the {@code GameResultDTO} to send
   * @return the saved {@code GameResultDTO} (201)
   * @throws {@code ResponseStatusException} (500) when the access token is invalid
   */
  @PostMapping("/results")
  @ResponseStatus(HttpStatus.CREATED)
  public GameResultDTO saveGameResult(
    @CookieValue("access_token") final String accessToken,
    @RequestBody final GameResultDTO gameResultDTO
  ) {
    jwtValidatorService.validateTokenOrThrow(accessToken);
    final String userId = jwtValidatorService.extractUserId(accessToken);
    log.debug("save game result for userId {}: {}", userId, gameResultDTO);
    gameResultService.saveGameResult(gameResultDTO, userId, accessToken);
    return gameResultDTO;
  }
}
