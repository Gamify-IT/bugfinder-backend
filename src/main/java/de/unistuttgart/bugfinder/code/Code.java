package de.unistuttgart.bugfinder.code;

import de.unistuttgart.bugfinder.code.word.Word;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Code {

    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Word> words;

    public Code(final List<Word> words) {
        this.words = words;
    }

    public void addWord(final Word word) {
        this.words.add(word);
    }

    public void removeWord(final Word word) {
        this.words.remove(word);
    }

}
