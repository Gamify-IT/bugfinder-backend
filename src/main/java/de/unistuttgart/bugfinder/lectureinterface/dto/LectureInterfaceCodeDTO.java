package de.unistuttgart.bugfinder.lectureinterface.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

/**
 * describes the model of a code from the view point of the lecture interface
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectureInterfaceCodeDTO {

    /**
     * outer list describes lines of code, inner list describes pieces of code.
     */
    List<List<LectureInterfaceWordDTO>> words = new ArrayList<>();
}
