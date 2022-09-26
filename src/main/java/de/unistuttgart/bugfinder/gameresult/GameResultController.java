package de.unistuttgart.bugfinder.gameresult;

import de.unistuttgart.gamifyit.authentificationvalidator.JWTValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class GameResultController {

  @Autowired
  private GameResultService gameResultService;

  @Autowired
  private JWTValidatorService jwtValidatorService;

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
