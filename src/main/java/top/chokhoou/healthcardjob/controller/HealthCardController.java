package top.chokhoou.healthcardjob.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chokhoou.healthcardjob.service.HealthCardJobService;
import top.chokhoou.healthcardjob.service.HealthCardService;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Controller
@RequestMapping("/api/v1")
public class HealthCardController {

    @Autowired
    private HealthCardJobService healthCardJobService;

    @ResponseBody
    @GetMapping("/commit")
    public void commit() {
        healthCardJobService.execute();
    }

}
