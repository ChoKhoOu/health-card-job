package top.chokhoou.healthcardjob.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 填卡定时任务
 *
 * @author ChoKhoOu
 */
@Component
public class HealthCardJob {
    @Scheduled(cron = "0 0 9 * * ?")
    public void commitHealthCard() {

    }
}
