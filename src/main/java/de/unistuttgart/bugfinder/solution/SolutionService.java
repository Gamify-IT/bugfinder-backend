package de.unistuttgart.bugfinder.solution;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import de.unistuttgart.bugfinder.solution.bug.BugMapper;
import de.unistuttgart.bugfinder.solution.bug.BugService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class SolutionService {

  @Autowired
  private BugService bugService;

  @Autowired
  private SolutionRepository solutionRepository;

  @Autowired
  private SolutionMapper solutionMapper;

  @Autowired
  private BugMapper bugMapper;

  public List<SolutionDTO> findAll() {
    log.info("get all solutions");
    return solutionMapper.toDTO(solutionRepository.findAll());
  }

  public Optional<SolutionDTO> find(final UUID id) {
    log.info("get solution {}", id);
    return solutionMapper.toDTO(solutionRepository.findById(id));
  }

  public SolutionDTO save(final SolutionDTO solutionDTO) {
    log.info("create solution {}", solutionDTO);
    return solutionMapper.toDTO(solutionRepository.save(solutionMapper.fromDTO(solutionDTO)));
  }

  public void delete(final UUID id) {
    log.info("delete solution {}", id);
    solutionRepository.deleteById(id);
  }

  public BugDTO addBug(final UUID id, final BugDTO bug) {
    log.info("add bug {} to solution {}", bug, id);
    final Solution solution = solutionRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("Unable to find solution with id %s", id))
      );
    final BugDTO savedBug = bugService.save(bug);
    solution.addBug(bugMapper.fromDTO(savedBug));
    solutionRepository.save(solution);
    return savedBug;
  }

  public BugDTO removeBug(final UUID id, final UUID bugId) {
    log.info("remove bug {} from solution {}", bugId, id);
    final Solution solution = solutionRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("Unable to find solution with id %s", id))
      );
    final BugDTO bug = bugService
      .find(bugId)
      .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("Unable to find bug with id %s", id)));
    solution.removeBug(bugMapper.fromDTO(bug));
    solutionRepository.save(solution);
    return bug;
  }

  public List<BugDTO> getBugs(final UUID id) {
    log.info("get bugs from solution {}", id);
    final Solution solution = solutionRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("Unable to find solution with id %s", id))
      );
    return bugMapper.toDTO(solution.getBugs());
  }
}
