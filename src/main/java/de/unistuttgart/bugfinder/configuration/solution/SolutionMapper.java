package de.unistuttgart.bugfinder.configuration.solution;

import de.unistuttgart.bugfinder.configuration.code.CodeMapper;
import de.unistuttgart.bugfinder.configuration.solution.bug.BugMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps Solution to SolutionDTO and vice versa.
 */
@Component
@FieldDefaults(level = AccessLevel.PUBLIC)
public class SolutionMapper {

    @Autowired
    private BugMapper bugMapper;

    @Autowired
    private CodeMapper codeMapper;

    /**
     * Maps a {@code Solution} to a {@code SolutionDTO}.
     *
     * @param solution the {@code Solution} to map
     * @return the mapped {@code SolutionDTO}
     */
    public SolutionDTO toDTO(final Solution solution) {
        return new SolutionDTO(solution.getId(), bugMapper.toDTO(solution.getBugs()), codeMapper.toDTO(solution.getCode()));
    }

    /**
     * Maps a collection {@code Solution} to a list {@code SolutionDTO}.
     *
     * @param solutions the collection of {@code Solution} to map
     * @return the mapped {@code SolutionDTO} list
     */
    public List<SolutionDTO> toDTO(final Collection<Solution> solutions) {
        return solutions.parallelStream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Maps an optional {@code Solution} to an optional {@code SolutionDTO}.
     *
     * @param solution the optional {@code Solution} to map
     * @return the mapped optional {@code SolutionDTO}
     */
    public Optional<SolutionDTO> toDTO(final Optional<Solution> solution) {
        return solution.map(this::toDTO);
    }


    /**
     * Maps a {@code SolutionDTO} to a {@code Solution}.
     *
     * @param solutionDTO the {@code SolutionDTO} to map
     * @return the mapped {@code Solution}
     */
    public Solution fromDTO(final SolutionDTO solutionDTO) {
        return new Solution(
                solutionDTO.getId(),
                new HashSet<>(bugMapper.fromDTO(solutionDTO.getBugs())),
                codeMapper.fromDTO(solutionDTO.getCode())
        );
    }

    /**
     * Maps a list {@code SolutionDTO} to a collection {@code Solution}.
     *
     * @param solutionDTOs the list of {@code SolutionDTO} to map
     * @return the mapped {@code Solution} collection
     */
    public List<Solution> fromDTO(final Collection<SolutionDTO> solutionDTOs) {
        return solutionDTOs.parallelStream().map(this::fromDTO).collect(Collectors.toList());
    }

    /**
     * Maps an optional {@code SolutionDTO} to an optional {@code Solution}.
     *
     * @param solutionDTO the optional {@code SolutionDTO} to map
     * @return the mapped optional {@code Solution}
     */
    public Optional<Solution> fromDTO(final Optional<SolutionDTO> solutionDTO) {
        return solutionDTO.map(this::fromDTO);
    }
}
