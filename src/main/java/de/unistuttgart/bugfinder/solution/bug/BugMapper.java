package de.unistuttgart.bugfinder.solution.bug;

import de.unistuttgart.bugfinder.code.word.WordMapper;
import de.unistuttgart.bugfinder.code.word.WordRepository;
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

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class BugMapper {

  @Autowired
  private WordMapper wordMapper;

  @Autowired
  private WordRepository wordRepository;

  public BugDTO toDTO(final Bug bug) {
    return new BugDTO(bug.getId(), bug.getWord().getId(), bug.getErrorType(), bug.getCorrectValue());
  }

  public List<BugDTO> toDTO(final Collection<Bug> bugs) {
    return bugs.parallelStream().map(this::toDTO).collect(Collectors.toList());
  }

  public Optional<BugDTO> toDTO(final Optional<Bug> bug) {
    return bug.map(this::toDTO);
  }

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

  public List<Bug> fromDTO(final Collection<BugDTO> bugDTOs) {
    return bugDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
  }

  public Optional<Bug> fromDTO(final Optional<BugDTO> bugDTO) {
    return bugDTO.map(this::fromDTO);
  }
}
