package de.unistuttgart.bugfinder.configuration.lectureinterface.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * describes the view model of the lecture interface and is used to build configurations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectureInterfaceConfigurationDTO {

  List<LectureInterfaceCodeDTO> codes;
}
