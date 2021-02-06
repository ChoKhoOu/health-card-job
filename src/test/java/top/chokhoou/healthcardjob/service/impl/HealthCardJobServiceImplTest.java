package top.chokhoou.healthcardjob.service.impl;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.chokhoou.healthcardjob.service.HealthCardJobService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HealthCardJobServiceImplTest {

    @Autowired
    HealthCardJobService healthCardJobService;
    @Test
    void execute() {
        healthCardJobService.execute();
    }
}