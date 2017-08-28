package oleg.sopilnyak.common.service;

import java.util.UUID;

/**
 * Service to generate unique String id (UUID)
 */
public interface IdGeneratorService {

    /**
     * UUID generation
     *
     * @return generated UUID
     */
    UUID generateUUID();

    /**
     * Unique string generation
     *
     * @return unique id
     */
    String generate();

}
