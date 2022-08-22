package de.unistuttgart.bugfinder.configuration;

import de.unistuttgart.bugfinder.code.CodeDTO;
import de.unistuttgart.bugfinder.code.CodeMapper;
import de.unistuttgart.bugfinder.code.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ConfigurationService {

    @Autowired
    private CodeService codeService;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private CodeMapper codeMapper;

    public List<ConfigurationDTO> findAll() {
        log.info("get all configurations");
        return configurationMapper.toDTO(configurationRepository.findAll());
    }

    public Optional<ConfigurationDTO> find(final String id) {
        log.info("get configuration {}", id);
        return configurationMapper.toDTO(configurationRepository.findById(UUID.fromString(id)));
    }

    public ConfigurationDTO save(final ConfigurationDTO configurationDTO) {
        log.info("create configuration {}", configurationDTO);
        return configurationMapper.toDTO(configurationRepository.save(configurationMapper.fromDTO(configurationDTO)));
    }

    public void delete(final String id) {
        log.info("delete configuration {}", id);
        configurationRepository.deleteById(UUID.fromString(id));
    }

    public CodeDTO addCode(final String id, final CodeDTO code) {
        log.info("add code {} to configuration {}", code, id);
        final Configuration configuration = configurationRepository.findById(UUID.fromString(id)).orElseThrow();
        final CodeDTO savedCode = codeService.save(code);
        configuration.addCode(codeMapper.fromDTO(savedCode));
        configurationRepository.save(configuration);
        return savedCode;
    }

    public CodeDTO removeCode(final String id, final String codeId) {
        log.info("remove code {} from configuration {}", codeId, id);
        final Configuration configuration = configurationRepository.findById(UUID.fromString(id)).orElseThrow();
        final CodeDTO persistedCode = codeService.find(id).orElseThrow();
        configuration.removeCode(codeMapper.fromDTO(persistedCode));
        configurationRepository.save(configuration);
        return null;
    }

    public List<CodeDTO> getCodes(final String id) {
        log.info("get codes from configuration {}", id);
        final Configuration configuration = configurationRepository.findById(UUID.fromString(id)).orElseThrow();
        return codeMapper.toDTO(configuration.getCodes());
    }
}
