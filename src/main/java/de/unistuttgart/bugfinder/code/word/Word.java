package de.unistuttgart.bugfinder.code.word;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Word {

    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;

    String word;

    public Word(final String word) {
        this.word = word;
    }
}
