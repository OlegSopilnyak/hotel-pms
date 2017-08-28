package oleg.sopilnyak.common.service.impl;

import oleg.sopilnyak.common.service.IdGeneratorService;

import java.util.UUID;

/**
 * Realization of service
 * @see oleg.sopilnyak.common.service.IdGeneratorService
 */
public class IdGeneratorServiceImpl implements IdGeneratorService{
    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    @Override
    public String generate() {
        return generateUUID().toString();
    }
}
