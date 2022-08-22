package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.solution.bug.BugMapper;
import de.unistuttgart.bugfinder.util.UuidUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class SolutionMapper {

    @Autowired
    private BugMapper bugMapper;

    public SolutionDTO toDTO(final Solution solution) {
        return new SolutionDTO(solution.getId().toString(), bugMapper.toDTO(solution.getBugs()));
    }

    public List<SolutionDTO> toDTO(final Collection<Solution> solutions) {
        return solutions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<SolutionDTO> toDTO(final Optional<Solution> solution) {
        return solution.isEmpty() ? Optional.empty() : Optional.of(toDTO(solution.get()));
    }

    public Solution fromDTO(final SolutionDTO solutionDTO) {
        return new Solution(
                UuidUtil.ofNullableFallbackNull(solutionDTO.getId()),
                new HashSet<>(bugMapper.fromDTO(solutionDTO.getBugs()))
        );
    }

    public List<Solution> fromDTO(final Collection<SolutionDTO> solutionDTOs) {
        return solutionDTOs.stream().map(this::fromDTO).collect(Collectors.toList());
    }

    public Optional<Solution> fromDTO(final Optional<SolutionDTO> solutionDTO) {
        return solutionDTO.isEmpty() ? Optional.empty() : Optional.of(fromDTO(solutionDTO.get()));
    }
}
