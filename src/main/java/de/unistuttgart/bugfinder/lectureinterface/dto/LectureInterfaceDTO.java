package de.unistuttgart.bugfinder.lectureinterface.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * describes the model of a configuration from the view point of the lecture interface
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectureInterfaceDTO {

  /**
   * the codes of the configuration
   */
  List<LectureInterfaceCodeDTO> codes = new ArrayList<>();
}
