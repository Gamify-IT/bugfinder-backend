package de.unistuttgart.bugfinder.configuration.solution.bug;

import de.unistuttgart.bugfinder.configuration.code.word.WordMapper;
import de.unistuttgart.bugfinder.configuration.code.word.WordRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Maps {@code Bug} to {@code BugDTO} and vice versa.
 */
@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class BugMapper {

  @Autowired
  private WordMapper wordMapper;

  @Autowired
  private WordRepository wordRepository;

  /**
   * Maps a {@code Bug} to a {@code BugDTO}.
   *
   * @param bug the {@code Bug} to map
   * @return the mapped {@code BugDTO}
   */
  public BugDTO toDTO(final Bug bug) {
    return new BugDTO(bug.getId(), bug.getWord().getId(), bug.getErrorType(), bug.getCorrectValue());
  }

  /**
   * Maps a collection {@code Bug} to a list {@code BugDTO}.
   *
   * @param bugs the collection of {@code Bug} to map
   * @return the mapped {@code BugDTO} list
   */
  public List<BugDTO> toDTO(final Collection<Bug> bugs) {
    return bugs.parallelStream().map(this::toDTO).collect(Collectors.toList());
  }

  /**
   * Maps an optional {@code Bug} to an optional {@code BugDTO}.
   *
   * @param bug the optional {@code Bug} to map
   * @return the mapped optional {@code BugDTO}
   */
  public Optional<BugDTO> toDTO(final Optional<Bug> bug) {
    return bug.map(this::toDTO);
  }

  /**
   * Maps a {@code BugDTO} to a {@code Bug}.
   *
   * @param bugDTO the {@code BugDTO} to map
   * @return the mapped {@code Bug}
   */
  public Bug fromDTO(final BugDTO bugDTO) {
    return new Bug(
      bugDTO.getId(),
      wordRepository
        .findById(bugDTO.getWordId())
        .orElseThrow(() ->
          new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Word %s does not exist", bugDTO.getWordId()))
        ),
      bugDTO.getErrorType(),
      bugDTO.getCorrectValue()
    );
  }

  /**
   * Maps a collection {@code BugDTO} to a list {@code Bug}.
   *
   * @param bugDTOs the collection of {@code BugDTO} to map
   * @return the mapped {@code Bug} list
   */
  public List<Bug> fromDTO(final Collection<BugDTO> bugDTOs) {
    return bugDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
  }

  /**
   * Maps an optional {@code BugDTO} to an optional {@code Bug}.
   *
   * @param bugDTO the optional {@code BugDTO} to map
   * @return the mapped optional {@code Bug}
   */
  public Optional<Bug> fromDTO(final Optional<BugDTO> bugDTO) {
    return bugDTO.map(this::fromDTO);
  }
}
