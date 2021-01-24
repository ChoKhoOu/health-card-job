package top.chokhoou.healthcardjob.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.chokhoou.healthcardjob.entity.CardCommitLog;

/**
 * @author ChoKhoOu
 */
@Repository
public interface CardCommitLogDao extends JpaRepository<CardCommitLog, Long> {
}
