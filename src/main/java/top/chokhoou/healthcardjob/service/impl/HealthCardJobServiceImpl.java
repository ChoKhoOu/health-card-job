package top.chokhoou.healthcardjob.service.impl;

import cn.hutool.core.thread.ExecutorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import top.chokhoou.healthcardjob.common.constants.NacosConst;
import top.chokhoou.healthcardjob.entity.Card;
import top.chokhoou.healthcardjob.service.HealthCardJobService;
import top.chokhoou.healthcardjob.service.HealthCardService;
import top.chokhoou.healthcardjob.util.CustomThreadFactory;
import top.chokhoou.healthcardjob.util.NacosUtil;

import java.net.HttpCookie;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Service
public class HealthCardJobServiceImpl implements HealthCardJobService {

    @Autowired
    private HealthCardService healthCardService;

    @Override
    public void execute() {
        ThreadPoolExecutor executor = ExecutorBuilder.create()
                .setCorePoolSize(10)
                .setMaxPoolSize(10)
                .setWorkQueue(new LinkedBlockingQueue<>())
                .setThreadFactory(new CustomThreadFactory("health-card-job-thread-"))
                .build();

        HttpCookie[] cookies = healthCardService.getCookies();
        List<String> studentIds = NacosUtil.healthCard().getConfigObjectList(NacosConst.STUDENT_IDS, String.class);
        StopWatch getStudentsWatch = new StopWatch();

        getStudentsWatch.start();
        log.info("Health card job: get students start.");
        List<Card> all = studentIds.parallelStream().map(e -> healthCardService.getStudent(e, cookies)).collect(Collectors.toList());
        getStudentsWatch.stop();
        log.info("Health card job: get students done. It take {} s.", getStudentsWatch.getTotalTimeSeconds());

        for (Card card : all) {
            executor.execute(() -> {
                StopWatch commitWatch = new StopWatch();
                log.info("Health card job: commit card start. studentId={}", card.getGh());
                commitWatch.start();
                healthCardService.commitHealthCard(card, cookies);
                commitWatch.stop();
                log.info("Health card job: commit done. StudentId is {}. It take {} s. ", card.getGh(), commitWatch.getTotalTimeSeconds());
            });
        }
    }
}
