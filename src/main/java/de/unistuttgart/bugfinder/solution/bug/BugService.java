package de.unistuttgart.bugfinder.solution.bug;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BugService {

  @Autowired
  private BugRepository bugRepository;

  @Autowired
  private BugMapper bugMapper;

  public List<BugDTO> findAll() {
    log.debug("get all bugs");
    return bugMapper.toDTO(bugRepository.findAll());
  }

  public Optional<BugDTO> find(final UUID id) {
    log.debug("get bug {}", id);
    return bugMapper.toDTO(bugRepository.findById(id));
  }

  public BugDTO save(final BugDTO bugDTO) {
    log.debug("create bug {}", bugDTO);
    return bugMapper.toDTO(bugRepository.save(bugMapper.fromDTO(bugDTO)));
  }

  public void delete(final UUID id) {
    log.debug("delete bug {}", id);
    bugRepository.deleteById(id);
  }
}
