package oleg.sopilnyak.common.service.impl;

import java.util.UUID;

import static org.junit.Assert.assertNotEquals;

public class IdGeneratorServiceImplTest {

    private final IdGeneratorServiceImpl service = new IdGeneratorServiceImpl();
    @org.junit.Test
    public void generateUUID() throws Exception {
        UUID id = service.generateUUID();
        assertNotEquals(id, service.generateUUID());
    }

    @org.junit.Test
    public void generate() throws Exception {
        String id = service.generate();
        assertNotEquals(id, service.generate());
    }

}