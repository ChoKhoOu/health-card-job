package top.chokhoou.healthcardjob.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ChoKhoOu
 */
@Data
@Entity
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class CardCommitLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;

    private String state;

    @CreatedDate
    private LocalDateTime createTime;
}
