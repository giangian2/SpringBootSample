package com.sy.RAWWAR.ws;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sy.RAWWAR.repository.MissionRepository;

@SpringBootTest
public class MissionRepositoryTest {
    @Autowired
    private MissionRepository missionRepo;

    @Test
    void contextLoads() throws Exception {
        assertNotNull(missionRepo);
    }
}
