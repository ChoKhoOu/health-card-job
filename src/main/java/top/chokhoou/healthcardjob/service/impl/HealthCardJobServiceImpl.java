package top.chokhoou.healthcardjob.service.impl;

import cn.hutool.core.thread.ExecutorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import top.chokhoou.healthcardjob.common.config.StudentHealthCard;
import top.chokhoou.healthcardjob.entity.Card;
import top.chokhoou.healthcardjob.service.HealthCardJobService;
import top.chokhoou.healthcardjob.service.HealthCardService;

import java.net.HttpCookie;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Service
public class HealthCardJobServiceImpl implements HealthCardJobService {

    @Autowired
    private HealthCardService healthCardService;

    @Autowired
    StudentHealthCard studentInfo;

    @Override
    public void execute() {
        ThreadPoolExecutor executor = ExecutorBuilder.create()
                .setCorePoolSize(10)
                .setMaxPoolSize(10)
                .setWorkQueue(new LinkedBlockingQueue<>())
                .setThreadFactory(new HealthCardThreadFactory())
                .build();

        HttpCookie[] cookies = healthCardService.getCookies();
        List<String> studentIds = studentInfo.getStudentIds();
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

    public static class HealthCardThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public HealthCardThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "health-card-job-thread-";
        }


        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }

    }
}
