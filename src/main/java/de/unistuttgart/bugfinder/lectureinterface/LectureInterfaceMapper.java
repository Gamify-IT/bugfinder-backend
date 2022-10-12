package de.unistuttgart.bugfinder.lectureinterface;

import de.unistuttgart.bugfinder.configuration.code.Code;
import de.unistuttgart.bugfinder.configuration.code.word.Word;
import de.unistuttgart.bugfinder.configuration.code.word.WordRepository;
import de.unistuttgart.bugfinder.configuration.Configuration;
import de.unistuttgart.bugfinder.lectureinterface.dto.LectureInterfaceCodeDTO;
import de.unistuttgart.bugfinder.lectureinterface.dto.LectureInterfaceDTO;
import de.unistuttgart.bugfinder.lectureinterface.dto.LectureInterfaceWordDTO;
import de.unistuttgart.bugfinder.configuration.solution.Solution;
import de.unistuttgart.bugfinder.configuration.solution.SolutionRepository;
import de.unistuttgart.bugfinder.configuration.solution.bug.Bug;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Non-trivial mapper for the lecture interface that maps a {@code Configuration} to a {@code LectureInterfaceDTO} and vice versa.
 */
@Component
public class LectureInterfaceMapper {

    public static final String WORD_SPACE = " ";
    public static final String WORD_NEW_LINE = "\n";
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private WordRepository wordRepository;

    /**
     * maps a {@code Configuration} to a {@code LectureInterfaceDTO}
     *
     * @param configuration the configuration to map
     * @return the mapped {@code LectureInterfaceDTO}
     */
    public LectureInterfaceDTO configurationToLectureInterfaceDTO(Configuration configuration) {
        return new LectureInterfaceDTO(
                configuration.getCodes().stream().map(this::mapCodeToLectureInterfaceCodeDTO).collect(Collectors.toList())
        );
    }

    /**
     * maps a {@code code} to a {@code LectureInterfaceCodeDTO} by splitting the code into lines and then mapping each line to a {@code LectureInterfaceWordDTO}
     *
     * @param code the code to map
     * @return the mapped {@code LectureInterfaceCodeDTO}
     */
    private LectureInterfaceCodeDTO mapCodeToLectureInterfaceCodeDTO(Code code) {
        final Solution solution = getSolutionOrThrow(code);

        return new LectureInterfaceCodeDTO(
                mapCodeToLines(code)
                        .stream()
                        .map(line -> mapCodeLineToLectureInterfaceWordLine(line, solution))
                        .collect(Collectors.toList())
        );
    }

    /**
     * maps a list of {@code Word}s of code to a list of {@code LectureInterfaceWordDTO}s.
     * <p>
     * Both lists of words represent a line of code.
     * </p>
     * <p>
     * we store spaces in between each word because then in the game they can be clicked and fixed if wanted. The
     * lecture interface doesn't need this information. In order to remove the spaces we spot spaces that doesn't
     * contain a bug and remove it.
     * </p>
     *
     * @param line     the line of code to map
     * @param solution the solution to map
     * @return the mapped {@code LectureInterfaceWordDTO}s
     */
    private List<LectureInterfaceWordDTO> mapCodeLineToLectureInterfaceWordLine(List<Word> line, Solution solution) {
        return line
                .stream()
                .map(word -> createLectureInterfaceWordDTO(word, solution))
                // remove all words that are blank and don't have a bug because it is a space between two words
                .filter(word -> !word.getCorrectValue().isBlank() || word.getErrorType() != null)
                .collect(Collectors.toList());
    }

    /**
     * maps {@code word} and {@code solution} to a {@code LectureInterfaceWordDTO} and also finds all bugs that are in
     * the database and is asociated with the {@code word}
     *
     * @param word     the word to map
     * @param solution the solution to map
     * @return the mapped {@code LectureInterfaceWordDTO}
     */
    private LectureInterfaceWordDTO createLectureInterfaceWordDTO(Word word, Solution solution) {
        Optional<Bug> bug = findOptionalBug(solution, word);
        LectureInterfaceWordDTO lectureInterfaceWordDTO = new LectureInterfaceWordDTO();
        if (bug.isPresent()) {
            lectureInterfaceWordDTO.setErrorType(bug.get().getErrorType());
            lectureInterfaceWordDTO.setCorrectValue(bug.get().getCorrectValue());
            lectureInterfaceWordDTO.setDisplayValue(word.getWord());
        } else {
            // if there is no bug we set the correct value to the word, because there is no error with the word.
            lectureInterfaceWordDTO.setCorrectValue(word.getWord());
        }
        return lectureInterfaceWordDTO;
    }

    /**
     * finds an optional bug in the {@code solution} that is associated with the {@code word}
     *
     * @param solution the solution to search in
     * @param word     the word to search for
     * @return the optional bug in the solution that is associated with the word
     */
    private Optional<Bug> findOptionalBug(Solution solution, Word word) {
        return solution.getBugs().stream().filter(b -> b.getWord().getId().equals(word.getId())).findFirst();
    }

    /**
     * split code into lines which is a list of all lines containing a list of all words in that line
     *
     * @param code the code to split
     * @return a list of lines that are lists of words
     */
    private List<List<Word>> mapCodeToLines(Code code) {
        List<List<Word>> result = new ArrayList<>();

        List<Word> line = new ArrayList<>();
        for (Word word : code.getWords()) {
            if (word.getWord().equals("\n")) {
                result.add(line);
                line = new ArrayList<>();
            } else {
                line.add(word);
            }
        }
        result.add(line);

        return result;
    }

    /**
     * gets the solution of the {@code code} or throws a {@code ResponseStatusException} if the solution doesn't exist
     *
     * @param code the code to get the solution for
     * @return the solution of the code
     * @throws ResponseStatusException if the solution doesn't exist
     */
    private Solution getSolutionOrThrow(Code code) {
        return solutionRepository
                .findByCodeId(code.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Solution with code id %s not found.", code.getId())
                        )
                );
    }

    /**
     * maps a {@code LectureInterfaceDTO} to a pair of {@code Code} and {@code Solution}
     *
     * @param lectureInterfaceCodeDTO the lecture interface code dto to map
     * @return a pair of code and solution
     */
    public CodeAndSolution lectureInterfaceToCodeSolutionPair(LectureInterfaceCodeDTO lectureInterfaceCodeDTO) {
        List<Word> wordsToPersistToCode = new ArrayList<>(lectureInterfaceCodeDTO.getWords().size());
        Set<Bug> bugsToPersistToSolution = new HashSet<>();

        for (List<LectureInterfaceWordDTO> row : lectureInterfaceCodeDTO.getWords()) {
            // check if the row only contains blank strings
            // the first row will never contain only blank strings since it comes from the lecture interface
            if (row.stream().allMatch(wordVM -> wordVM.getCorrectValue().isBlank())) {
                continue;
            }
            // if it is not the first row in the code we add a new line
            if (lectureInterfaceCodeDTO.getWords().indexOf(row) != 0) {
                wordsToPersistToCode.add(wordRepository.save(new Word(null, WORD_NEW_LINE)));
            }
            for (LectureInterfaceWordDTO lectureInterfaceWordDTO : row) {
                // check if the word is blank
                // the first word will never be blank since it comes from the lecture interface
                if (lectureInterfaceWordDTO.getCorrectValue().isBlank()) {
                    continue;
                }
                // if it is not the first word in the row we add a space
                if (row.indexOf(lectureInterfaceWordDTO) != 0) {
                    wordsToPersistToCode.add(wordRepository.save(new Word(null, WORD_SPACE)));
                }
                WordWithOptionalBug wordWithOptionalBugToPersist = lectureInterfaceWordToWordWithOptionalBug(lectureInterfaceWordDTO);
                wordsToPersistToCode.add(wordWithOptionalBugToPersist.getWord());
                wordWithOptionalBugToPersist.getBug().ifPresent(bugsToPersistToSolution::add);

            }
        }
        Code code = new Code(null, wordsToPersistToCode);
        return new CodeAndSolution(code, new Solution(null, bugsToPersistToSolution, code));
    }

    private WordWithOptionalBug lectureInterfaceWordToWordWithOptionalBug(LectureInterfaceWordDTO wordVM) {
        String displayValue = wordVM.getDisplayValue() != null ? wordVM.getDisplayValue() : wordVM.getCorrectValue();
        Word word = new Word(null, displayValue);
        Optional<Bug> optionalBug = mapWordToBug(word, wordVM);
        WordWithOptionalBug wordAndOptionalBugToPersist = new WordWithOptionalBug(word, optionalBug);
        return wordAndOptionalBugToPersist;
    }

    /**
     * maps a {@code Word} to an optional bug. We only need to return an bug when the lecture interface word dto has an
     * error type.
     *
     * @param word                    the word to map
     * @param lectureInterfaceWordDTO the lecture interface word dto to map
     * @return an optional bug
     */
    private Optional<Bug> mapWordToBug(Word word, LectureInterfaceWordDTO lectureInterfaceWordDTO) {
        if (lectureInterfaceWordDTO.getErrorType() != null) {
            return Optional.of(new Bug(word, lectureInterfaceWordDTO.getErrorType(), lectureInterfaceWordDTO.getCorrectValue()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * pair of {@code Code} and {@code Solution}
     */
    public static class CodeAndSolution {
        @Getter
        private final Code code;
        @Getter
        private final Solution solution;

        CodeAndSolution(Code code, Solution solution) {
            this.code = code;
            this.solution = solution;
        }
    }

    /**
     * pair of {@code Word} and optional {@code Bug}
     */
    private static class WordWithOptionalBug {
        @Getter
        private final Word word;
        @Getter
        private final Optional<Bug> bug;

        WordWithOptionalBug(Word word, Optional<Bug> bug) {
            this.word = word;
            this.bug = bug;
        }
    }
}
