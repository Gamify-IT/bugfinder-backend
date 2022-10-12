package de.unistuttgart.bugfinder.lectureinterface.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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
