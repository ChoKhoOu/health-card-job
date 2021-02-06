package top.chokhoou.healthcardjob.util.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ChoKhoOu
 */
public class CacheHolder {
    private volatile static Cache<String, Map<String, String>> studentCardCommitLogCache;

    public static Cache<String, Map<String, String>> getStudentCommitLogCache() {
        if (studentCardCommitLogCache == null) {
            synchronized (CacheHolder.class) {
                if (studentCardCommitLogCache == null) {
                    studentCardCommitLogCache = Caffeine.newBuilder()
                            .maximumSize(10)
                            .expireAfterWrite(2, TimeUnit.DAYS)
                            .build();
                }
            }
        }
        return studentCardCommitLogCache;
    }

}
