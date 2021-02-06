package top.chokhoou.healthcardjob.common.config;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ChoKhoOu
 */
@Data
@Configuration
@NacosConfigurationProperties(dataId = "HealthCardConfig", groupId = "HealthCard", autoRefreshed = true, type = ConfigType.YAML)
public class StudentHealthCard {
    private List<String> studentIds;
}
