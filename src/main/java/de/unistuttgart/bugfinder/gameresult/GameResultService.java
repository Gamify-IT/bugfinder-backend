package de.unistuttgart.bugfinder.gameresult;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class GameResultService {

    @Autowired
    private ResultClient resultClient;

    public void saveGameResult(GameResultDTO gameResultDTO, String userId) {
        final OverworldResultDTO resultDTO = new OverworldResultDTO(
                "BUGFINDER",
                gameResultDTO.getConfigurationId(),
                calculateScore(gameResultDTO),
                userId
        );
        try {
            resultClient.submit(resultDTO);
        } catch (FeignException.BadGateway badGateway) {
            String warning =
                    "The Overworld backend is currently not available. The result was NOT saved. Please try again later";
            log.warn(warning + badGateway);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, warning);
        } catch (FeignException.NotFound notFound) {
            String warning = "The result could not be saved. Unknown User";
            log.warn(warning + notFound);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, warning);
        }
    }

    private long calculateScore(GameResultDTO gameResultDTO) {
        // TODO: calculate score
        return 69;
    }
}
