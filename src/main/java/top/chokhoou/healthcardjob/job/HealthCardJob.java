package top.chokhoou.healthcardjob.job;

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.chokhoou.healthcardjob.service.HealthCardJobService;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 填卡定时任务
 *
 * @author ChoKhoOu
 */
@Component
public class HealthCardJob {
    @Autowired
    HealthCardJobService healthCardJobService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void execute() {
        healthCardJobService.execute();
    }
}
