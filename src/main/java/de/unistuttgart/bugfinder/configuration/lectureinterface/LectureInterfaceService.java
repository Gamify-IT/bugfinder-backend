package de.unistuttgart.bugfinder.configuration.lectureinterface;

import de.unistuttgart.bugfinder.code.Code;
import de.unistuttgart.bugfinder.code.CodeRepository;
import de.unistuttgart.bugfinder.code.word.Word;
import de.unistuttgart.bugfinder.code.word.WordRepository;
import de.unistuttgart.bugfinder.configuration.*;
import de.unistuttgart.bugfinder.configuration.lectureinterface.dto.LectureInterfaceCodeDTO;
import de.unistuttgart.bugfinder.configuration.lectureinterface.dto.LectureInterfaceConfigurationDTO;
import de.unistuttgart.bugfinder.configuration.lectureinterface.dto.LectureInterfaceWordDTO;
import de.unistuttgart.bugfinder.solution.Solution;
import de.unistuttgart.bugfinder.solution.SolutionRepository;
import de.unistuttgart.bugfinder.solution.bug.Bug;
import de.unistuttgart.bugfinder.solution.bug.BugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
public class LectureInterfaceService {


    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ConfigurationService configurationService;

    public ConfigurationDTO save(final LectureInterfaceConfigurationDTO lectureInterfaceConfiguration) {
        Set<Code> codesToPersist = new HashSet<>(lectureInterfaceConfiguration.getCodes().size());
        for (LectureInterfaceCodeDTO lectureInterfaceCode : lectureInterfaceConfiguration.getCodes()) {
            List<Word> wordsToPersistToCode = new ArrayList<>(lectureInterfaceCode.getWords().size());
            Set<Bug> bugsToPersistToSolution = new HashSet<>();
            bugsToPersistToSolution = new HashSet<>();
            for (List<LectureInterfaceWordDTO> row : lectureInterfaceCode.getWords()) {
                // check if the row only contains blank strings
                // the first row will never contain only blank strings since it comes from the lecture interface
                if (row.stream().allMatch(lectureInterfaceWord -> lectureInterfaceWord.getCorrectValue().isBlank())) {
                    continue;
                }
                // if it is not the first row in the code we add a new line
                if (lectureInterfaceCode.getWords().indexOf(row) != 0) {
                    wordsToPersistToCode.add(wordRepository.save(new Word(null, "\n")));
                }
                for (LectureInterfaceWordDTO lectureInterfaceWord : row) {
                    // check if the word is blank
                    // the first word will never be blank since it comes from the lecture interface
                    if (lectureInterfaceWord.getCorrectValue().isBlank()) {
                        continue;
                    }
                    // if it is not the first word in the row we add a space
                    if (row.indexOf(lectureInterfaceWord) != 0) {
                        wordsToPersistToCode.add(wordRepository.save(new Word(null, " ")));
                    }
                    String displayValue = lectureInterfaceWord.getDisplayValue() != null ? lectureInterfaceWord.getDisplayValue() : lectureInterfaceWord.getCorrectValue();
                    Word word = new Word(null, displayValue);
                    word = wordRepository.save(word);
                    wordsToPersistToCode.add(word);
                    if (lectureInterfaceWord.getErrorType() != null) {
                        Bug bug = new Bug(word, lectureInterfaceWord.getErrorType(), lectureInterfaceWord.getCorrectValue());
                        bug = bugRepository.save(bug);
                        bugsToPersistToSolution.add(bug);
                    }
                }
            }
            Code code = new Code(null, wordsToPersistToCode);
            codesToPersist.add(code);
            Solution solution = new Solution(null, bugsToPersistToSolution, code);
            code = codeRepository.save(code);
            solution = solutionRepository.save(solution);
        }
        Configuration configuration = new Configuration(null, codesToPersist);
        return configurationMapper.toDTO(configurationRepository.save(configuration));
    }

    public LectureInterfaceConfigurationDTO get(UUID id) {
        log.debug("get configuration view model {}", id);
        final Configuration configuration = configurationService.getConfiguration(id);
        final LectureInterfaceConfigurationDTO lectureInterfaceConfiguration = new LectureInterfaceConfigurationDTO(new ArrayList<>());
        for (Code code : configuration.getCodes()) {
            lectureInterfaceConfiguration.getCodes().add(getLectureInterfaceCodeDTO(code));
        }

        return lectureInterfaceConfiguration;
    }

    private LectureInterfaceCodeDTO getLectureInterfaceCodeDTO(Code code) {
        final LectureInterfaceCodeDTO lectureInterfaceCode = new LectureInterfaceCodeDTO(new ArrayList<>());
        final Solution solution = getSolutionOrThrow(code);

        mapRows(code, lectureInterfaceCode, solution);
        return lectureInterfaceCode;
    }

    private void mapRows(Code code, LectureInterfaceCodeDTO lectureInterfaceCode, Solution solution) {
        List<LectureInterfaceWordDTO> row = new ArrayList<>();
        for (Word word : code.getWords()) {
            if (isNewLine(word)) {
                lectureInterfaceCode.getWords().add(row);
                row = new ArrayList<>();
                continue;
            }
            Optional<Bug> bug = solution
                    .getBugs()
                    .stream()
                    .filter(b -> b.getWord().getId().equals(word.getId()))
                    .findFirst();
            if (isSpace(word) && bug.isEmpty()) {
                continue;
            }
            final LectureInterfaceWordDTO lectureInterfaceWord = mapWordWithOptionalBug(word, bug);
            row.add(lectureInterfaceWord);
        }
        lectureInterfaceCode.getWords().add(row);
    }

    private LectureInterfaceWordDTO mapWordWithOptionalBug(Word word, Optional<Bug> bug) {
        final LectureInterfaceWordDTO lectureInterfaceWord = new LectureInterfaceWordDTO();
        if (bug.isPresent()) {
            lectureInterfaceWord.setErrorType(bug.get().getErrorType());
            lectureInterfaceWord.setCorrectValue(bug.get().getCorrectValue());
            lectureInterfaceWord.setDisplayValue(word.getWord());
        } else {
            lectureInterfaceWord.setCorrectValue(word.getWord());
        }
        return lectureInterfaceWord;
    }

    private boolean isSpace(Word word) {
        return word.getWord().equals(" ");
    }

    private boolean isNewLine(Word word) {
        return word.getWord().equals("\n");
    }

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
}
