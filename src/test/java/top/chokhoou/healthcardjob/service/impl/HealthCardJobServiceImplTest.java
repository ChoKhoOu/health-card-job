package top.chokhoou.healthcardjob.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.chokhoou.healthcardjob.service.HealthCardJobService;

@SpringBootTest
class HealthCardJobServiceImplTest {

    @Autowired
    HealthCardJobService healthCardJobService;
    @Test
    void execute() {
        healthCardJobService.execute();
    }
}