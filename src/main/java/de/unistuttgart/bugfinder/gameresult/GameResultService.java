package de.unistuttgart.bugfinder.gameresult;

import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.configuration.Configuration;
import de.unistuttgart.bugfinder.configuration.ConfigurationRepository;
import de.unistuttgart.bugfinder.configuration.ConfigurationService;
import de.unistuttgart.bugfinder.solution.Solution;
import de.unistuttgart.bugfinder.solution.SolutionDTO;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import de.unistuttgart.bugfinder.solution.bug.Bug;
import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import feign.FeignException;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class GameResultService {

  static final long MAX_SCORE = 100l;

  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired
  private ConfigurationService configurationService;

  @Autowired
  private SolutionRepository solutionRepository;

  @Autowired
  private ResultClient resultClient;

  public void saveGameResult(final GameResultDTO gameResultDTO, final String userId) {
    final OverworldResultDTO resultDTO = new OverworldResultDTO(
      "BUGFINDER",
      gameResultDTO.getConfigurationId(),
      calculateScore(gameResultDTO),
      userId
    );
    try {
      resultClient.submit(resultDTO);
    } catch (final FeignException.BadGateway badGateway) {
      final String warning =
        "The Overworld backend is currently not available. The result was NOT saved. Please try again later.";
      log.warn(warning, badGateway);
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, warning);
    } catch (final FeignException.NotFound notFound) {
      String warning = String.format("The result could not be saved. Unknown User '%s'.", userId);
      log.warn(warning, notFound);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, warning);
    }
  }

  /**
   * Calculates the score of a game result which contains evaluating the solution of every code to calculate the score.
   *
   * @param gameResultDTO the players game result
   * @return a score between 0 and 100
   */
  private long calculateScore(final GameResultDTO gameResultDTO) {
    final Configuration configuration = configurationService.getConfiguration(gameResultDTO.getConfigurationId());
    final Map<Code, Long> codeScore = new HashMap<>();
    for (final Code code : configuration.getCodes()) {
      codeScore.put(code, calculateCodeScore(code, gameResultDTO));
    }
    final long score = calculateScoreFromCodeScores(codeScore);
    log.debug("Player got score of {} in configuration with ID {}", score, configuration.getId());
    return score;
  }

  /**
   * Calculates the score of a specific code
   *
   * @param code the code where the score should be calculated
   * @param gameResultDTO the players game result
   * @return a score between 0 and 100
   */
  private long calculateCodeScore(final Code code, final GameResultDTO gameResultDTO) {
    final Optional<Solution> solution = solutionRepository.findByCodeId(code.getId());
    if (solution.isEmpty()) {
      // if no solution is provided, give the player full points
      return MAX_SCORE;
    } else {
      final Optional<SubmittedSolutionDTO> playerSubmittedSolution = getSubmittedSolutionFromCode(
        gameResultDTO.getSubmittedSolutions(),
        code
      );
      if (playerSubmittedSolution.isEmpty()) {
        return 0l;
      }
      final SolutionDTO playerSolution = playerSubmittedSolution.get().getSolution();
      final int bugsSolved = (int) solution
        .get()
        .getBugs()
        .parallelStream()
        .filter(bug -> fixedBug(bug, playerSolution))
        .count();
      final List<BugDTO> falseSubmittedBugs = getFalseSubmittedBugs(solution.get(), playerSolution);
      final int bugsMissolved = falseSubmittedBugs.size();

      long score;
      if (solution.get().getBugs().isEmpty() && bugsMissolved == 0) {
        score = MAX_SCORE;
      } else {
        score = (Math.max(bugsSolved - bugsMissolved, 0) / Math.max(solution.get().getBugs().size(), 1)) * MAX_SCORE;
      }

      log.debug(
        "Solved {}/{} bugs, and falsely solved {} bugs",
        bugsSolved,
        solution.get().getBugs().size(),
        bugsMissolved
      );
      log.debug("Reached score of {} on code with ID {}", score, code.getId());
      return score;
    }
  }

  /**
   * Returns a list of bugs a player submitted which are according to the solutions no real bugs.
   *
   * @param solution the solution of the code entered by a lecturer
   * @param playerSolution the solution from the player
   * @return a list of bugs that are not bugs in the solution
   */
  private List<BugDTO> getFalseSubmittedBugs(final Solution solution, final SolutionDTO playerSolution) {
    return playerSolution
      .getBugs()
      .parallelStream()
      .filter(playerBug ->
        solution.getBugs().parallelStream().noneMatch(bug -> bug.getWord().getId().equals(playerBug.getWordId()))
      )
      .toList();
  }

  /**
   * Searches for the player's solution of a code.
   *
   * @param submittedSolutions the list of submitted solutions
   * @param code the code to search for the solution
   * @return an optional of a submitted solution from the player from a specific code
   */
  private Optional<SubmittedSolutionDTO> getSubmittedSolutionFromCode(
    final List<SubmittedSolutionDTO> submittedSolutions,
    final Code code
  ) {
    return submittedSolutions
      .parallelStream()
      .filter(submittedSolution -> submittedSolution.getCodeId().equals(code.getId()))
      .findAny();
  }

  /**
   * Calculate if a bug was fixed or not
   *
   * @param bug The bug with correct values
   * @param playersSolution the players solution
   * @return whether the bug was fixed or not
   */
  private boolean fixedBug(final Bug bug, final SolutionDTO playersSolution) {
    final Optional<BugDTO> playersBug = playersSolution
      .getBugs()
      .parallelStream()
      .filter(toFindPlayerBug -> toFindPlayerBug.getWordId().equals(bug.getWord().getId()))
      .findAny();
    if (playersBug.isEmpty()) {
      return false;
    }
    return (
      playersBug.get().getErrorType() == bug.getErrorType() &&
      playersBug.get().getCorrectValue().equals(bug.getCorrectValue())
    );
  }

  /**
   * Calculates the average score of a list of codes.
   *
   * @param codeScore a map with score for each code
   * @return the average score of all code scores
   */
  private long calculateScoreFromCodeScores(final Map<Code, Long> codeScore) {
    return (long) codeScore
      .values()
      .parallelStream()
      .mapToLong(l -> l)
      .filter(score -> score <= MAX_SCORE && score >= 0)
      .average()
      .orElse(MAX_SCORE);
  }
}
