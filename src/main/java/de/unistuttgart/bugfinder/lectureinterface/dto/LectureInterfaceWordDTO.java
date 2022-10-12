package de.unistuttgart.bugfinder.lectureinterface.dto;

import de.unistuttgart.bugfinder.solution.bug.ErrorType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

/**
 * describes the model of a word from the view point of the lecture interface.
 * <p>
 * the main difference to the {@code Word} model is that the {@code Word} contains a bug when there is a {@code Bug} pointing to the word.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectureInterfaceWordDTO {
    /**
     * the correct value of the word and the display value of the word when there is not error type.
     */
    String correctValue;

    /**
     * the displayed value of the word.
     * <p>
     * when null, the correct value is displayed.
     * </p>
     * <p>
     * is only allowed to be null when there is no error type.
     * </p>
     */
    @Nullable
    String displayValue;

    /**
     * the error type of the word.
     * <p>
     * when null, the word doesn't contain a bug.
     * </p>
     */
    @Nullable
    ErrorType errorType;
}
