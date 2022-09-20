package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.code.CodeMapper;
import de.unistuttgart.bugfinder.solution.bug.BugMapper;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class SolutionMapper {

  @Autowired
  private BugMapper bugMapper;

  @Autowired
  private CodeMapper codeMapper;

  public SolutionDTO toDTO(final Solution solution) {
    return new SolutionDTO(solution.getId(), bugMapper.toDTO(solution.getBugs()), codeMapper.toDTO(solution.getCode()));
  }

  public List<SolutionDTO> toDTO(final Collection<Solution> solutions) {
    return solutions.parallelStream().map(this::toDTO).collect(Collectors.toList());
  }

  public Optional<SolutionDTO> toDTO(final Optional<Solution> solution) {
    return solution.map(this::toDTO);
  }

  public Solution fromDTO(final SolutionDTO solutionDTO) {
    return new Solution(
      solutionDTO.getId(),
      new HashSet<>(bugMapper.fromDTO(solutionDTO.getBugs())),
      codeMapper.fromDTO(solutionDTO.getCode())
    );
  }

  public List<Solution> fromDTO(final Collection<SolutionDTO> solutionDTOs) {
    return solutionDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
  }

  public Optional<Solution> fromDTO(final Optional<SolutionDTO> solutionDTO) {
    return solutionDTO.map(this::fromDTO);
  }
}
