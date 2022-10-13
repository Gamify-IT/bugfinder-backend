package de.unistuttgart.bugfinder.lectureinterface;

import de.unistuttgart.bugfinder.configuration.ConfigurationDTO;
import de.unistuttgart.bugfinder.lectureinterface.dto.LectureInterfaceDTO;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * controller for the models used by the lecture interface
 */
@RestController("")
@Slf4j
public class LectureInterfaceController {

  @Autowired
  private LectureInterfaceService lectureInterfaceService;

  /**
   * get a lecture interface model from a configuration id.
   *
   * @param id the id of the {@code ConfigurationDTO} to get as a {@code LectureInterfaceDTO}
   * @return the found {@code LectureInterfaceDTO} (200)
   * @throws {@code ResponseStatusException} (404) when {@code ConfigurationDTO} with its id does not exist
   */
  @GetMapping("/lecture-interface/{id}")
  public LectureInterfaceDTO get(@PathVariable final UUID id) {
    log.debug("GET /lecture-interface/{}", id);
    return lectureInterfaceService.get(id);
  }

  /**
   * saves a {@code LectureInterfaceDTO} to the database.
   *
   * @param lectureInterfaceDTO the {@code LectureInterfaceDTO} to save
   * @return the saved {@code ConfigurationDTO} (201)
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/lecture-interface")
  public ConfigurationDTO save(@RequestBody final LectureInterfaceDTO lectureInterfaceDTO) {
    log.debug("POST /lecture-interface with body {}", lectureInterfaceDTO);
    return lectureInterfaceService.save(lectureInterfaceDTO);
  }
}
