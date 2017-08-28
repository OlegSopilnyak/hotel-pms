package oleg.sopilnyak.service.runner;

import lombok.extern.slf4j.Slf4j;
import oleg.sopilnyak.common.service.exception.ResourceNotFoundException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for error endpoint
 */
@Slf4j
@RestController
public class ErrorResponseController implements ErrorController {
    private static final String ERROR_MAPPING = "/error";

    @RequestMapping(value = ERROR_MAPPING)
    public ResponseEntity<String> error() throws ResourceNotFoundException {
        log.debug("Detected error");
        throw new ResourceNotFoundException("Something went wrong.");
    }

    @Override
    public String getErrorPath() {
        return ERROR_MAPPING;
    }
}
