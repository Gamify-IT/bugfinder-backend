package de.unistuttgart.bugfinder.solution;

import de.unistuttgart.bugfinder.solution.bug.BugDTO;
import de.unistuttgart.bugfinder.solution.bug.BugMapper;
import de.unistuttgart.bugfinder.solution.bug.BugService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Optional<SolutionDTO> find(final String id) {
    log.info("get solution {}", id);
    return solutionMapper.toDTO(solutionRepository.findById(UUID.fromString(id)));
  }

  public SolutionDTO save(final SolutionDTO solutionDTO) {
    log.info("create solution {}", solutionDTO);
    return solutionMapper.toDTO(solutionRepository.save(solutionMapper.fromDTO(solutionDTO)));
  }

  public void delete(final String id) {
    log.info("delete solution {}", id);
    solutionRepository.deleteById(UUID.fromString(id));
  }

  public BugDTO addBug(final String id, final BugDTO bug) {
    log.info("add bug {} to solution {}", bug, id);
    final Solution solution = solutionRepository.findById(UUID.fromString(id)).orElseThrow();
    solution.addBug(bugMapper.fromDTO(bug));
    solutionRepository.save(solution);
    return bug;
  }

  public BugDTO removeBug(final String id, final String bugId) {
    log.info("remove bug {} from solution {}", bugId, id);
    final Solution solution = solutionRepository.findById(UUID.fromString(id)).orElseThrow();
    final BugDTO bug = bugService.find(bugId).orElseThrow();
    solution.removeBug(bugMapper.fromDTO(bug));
    solutionRepository.save(solution);
    return bug;
  }

  public List<BugDTO> getBugs(final String id) {
    log.info("get bugs from solution {}", id);
    final Solution solution = solutionRepository.findById(UUID.fromString(id)).orElseThrow();
    return bugMapper.toDTO(solution.getBugs());
  }
}
